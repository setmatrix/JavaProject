package application;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javax.swing.*;

public class OrderController extends data implements Initializable {

	int loggedId;

	public void initData(int loggedId) {
		this.loggedId = loggedId;
	}

	@FXML
	private TableView<Orders> TableViewOrders;

	private void load() throws Throwable {
		String GAME_NAME;
		String PRODUCER;
		String PUBLISHER;
		String RELEASED;
		String PLATFORM;

		Connection connection = getConnection();

		String sql = " SELECT *" + "from Games ";
		try (PreparedStatement preStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preStatement.executeQuery();
			while (resultSet.next()) {
				GAME_NAME = resultSet.getString("GAME_NAME");
				PRODUCER = resultSet.getString("PRODUCER");
				PUBLISHER = resultSet.getString("PUBLISHER");
				RELEASED = resultSet.getString("RELEASED");
				PLATFORM = resultSet.getString("SNAME_PLATFORM");
				TableViewOrders.getItems().add(new Orders(GAME_NAME, PRODUCER, PUBLISHER, RELEASED, PLATFORM));
			}
		}
	}

	@FXML
	void ZawowAction() throws Throwable {
		if (TableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			Orders game = TableViewOrders.getSelectionModel().getSelectedItem();
			Connection connection = getConnection();
			String sql = "INSERT INTO Orders (ORDER_NAME, ORDER_DATE, IS_DELIVERED, CUSTOMER_ID)"
					+ " VALUES (?,?,1,?);";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				prestatement.setString(1, game.GAME_NAME);
				prestatement.setString(2, formatter.format(date));
				prestatement.setInt(3, loggedId);
				prestatement.executeUpdate();
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			setTable();
			load();
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Initialize Problem", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void setTable() {
		TableColumn<Orders, String> column;
		column = new TableColumn<>("GAME_NAME");
		column.setCellValueFactory(new PropertyValueFactory<>("GAME_NAME"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("PRODUCER");
		column.setCellValueFactory(new PropertyValueFactory<>("PRODUCER"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("PUBLISHER");
		column.setCellValueFactory(new PropertyValueFactory<>("PUBLISHER"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("RELEASED");
		column.setCellValueFactory(new PropertyValueFactory<>("RELEASED"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("PLATFORM");
		column.setCellValueFactory(new PropertyValueFactory<>("SNAMEPLATFORM"));
		TableViewOrders.getColumns().add(column);

	}

	@FXML
	void ZapiszAction() throws Throwable {
		String FIRST_NAME = null;
		String LAST_NAME = null;
		if (TableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setTitle("Open Resource File");
		    File file = fileChooser.showSaveDialog(null);
			FileWriter myWriter = new FileWriter(file);
			Orders game = TableViewOrders.getSelectionModel().getSelectedItem();
			Connection connection = getConnection();
			String sql = "Select FIRST_NAME, LAST_NAME\n" + "  from Users u\n"
					+ "  inner join Orders o  ON u.ID_USER = o.CUSTOMER_ID\n"
					+ "  inner join Games g on g.GAME_NAME = o.ORDER_NAME\n" + "  where o.ORDER_NAME = ?";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setString(1, game.GAME_NAME);
				ResultSet resultSet = prestatement.executeQuery();
				myWriter.write("Uzytkownicy ktorze posiadaja gre:" + game.GAME_NAME + "\n");
				while (resultSet.next()) {
					FIRST_NAME = resultSet.getString("FIRST_NAME");
					LAST_NAME = resultSet.getString("LAST_NAME");
					 myWriter.write(FIRST_NAME + " " + LAST_NAME + "\n");
					 
				}
				myWriter.close();
			}
		}
	}

}