package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import javax.swing.*;

public class OrderController extends Data implements Initializable {

	int loggedId;

	public void initData(int loggedId) {
		this.loggedId = loggedId;
	}

	@FXML
	private TableView<Orders> tableViewOrders;

	private void load() throws SQLException {
		String gameName;
		String producer;
		String publisher;
		String released;
		String platform;

		Connection connection = getConnection();

		String sql = " SELECT *" + "from Games ";
		try (PreparedStatement preStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preStatement.executeQuery();
			while (resultSet.next()) {
				gameName = resultSet.getString("GAME_NAME");
				producer = resultSet.getString("PRODUCER");
				publisher = resultSet.getString("PUBLISHER");
				released = resultSet.getString("RELEASED");
				platform = resultSet.getString("SNAME_PLATFORM");
				tableViewOrders.getItems().add(new Orders(gameName, producer, publisher, released, platform));
			}
		}
	}

	@FXML
	void zawowAction() throws SQLException {
		if (tableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			Orders game = tableViewOrders.getSelectionModel().getSelectedItem();
			Connection connection = getConnection();
			String sql = "INSERT INTO Orders (ORDER_NAME, ORDER_DATE, IS_DELIVERED, CUSTOMER_ID)"
					+ " VALUES (?,?,1,?);";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				prestatement.setString(1, game.getGAME_NAME());
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
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Initialize Problem", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void setTable() {
		setColumn(tableViewOrders, "GAME_NAME");
		setColumn(tableViewOrders, "PRODUCER");
		setColumn(tableViewOrders, "PUBLISHER");
		setColumn(tableViewOrders, "RELEASED");
		setColumn(tableViewOrders, "PLATFORM");

	}

	@FXML
	void zapiszAction(){
		String firstName;
		String lastName;
		if (tableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setTitle("Open Resource File");
		    File file = fileChooser.showSaveDialog(null);
			try (FileWriter myWriter = new FileWriter(file)) {
				Orders game = tableViewOrders.getSelectionModel().getSelectedItem();
				Connection connection = getConnection();
				String sql = "Select FIRST_NAME, LAST_NAME " + "  from Users u "
						+ "inner join Orders o ON ID_USER = o.CUSTOMER_ID "
						+ "inner join Games g on g.GAME_NAME = o.ORDER_NAME " + " where o.ORDER_NAME = ?";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setString(1, game.getGAME_NAME());
					ResultSet resultSet = prestatement.executeQuery();
					myWriter.write("Uzytkownicy ktorze posiadaja gre:" + game.getGAME_NAME() + "\n");
					while (resultSet.next()) {
						firstName = resultSet.getString("FIRST_NAME");
						lastName = resultSet.getString("LAST_NAME");
						myWriter.write(firstName+ " " +lastName + "\n");
					}
				}
			} catch (SQLException sq) {
				JOptionPane.showMessageDialog(null,sq.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
			}
			catch (IOException io)
			{
				JOptionPane.showMessageDialog(null,io.getMessage(), "IO Exception", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}