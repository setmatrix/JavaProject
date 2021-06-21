package application;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class OrderController extends data implements Initializable {

	int loggedId;

	public void initData(int loggedId) {
		this.loggedId = loggedId;
	}

	@FXML
	private TableView<Orders> TableViewOrders;

	private void load() throws SQLException {
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
				TableViewOrders.getItems().add(new Orders(GAME_NAME, PRODUCER, PUBLISHER, RELEASED,PLATFORM));
			}
		}
	}

	@FXML
	void ZawowAction() throws SQLException {
		if (TableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			Orders game = TableViewOrders.getSelectionModel().getSelectedItem();
			Connection connection = getConnection();
			String sql = "INSERT INTO Orders (ORDER_NAME, ORDER_DATE, IS_DELIVERED, CUSTOMER_ID)"
					+ " VALUES (?,?,1,?);";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				prestatement.setString(1, game.GAME_NAME);
				prestatement.setString(2, formatter.format(date));
				prestatement.setInt(3, loggedId);
				prestatement.executeUpdate();
			}
			catch (SQLException sq)
			{
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Order", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			setTable();
			load();
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Order", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void setTable(){
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
}