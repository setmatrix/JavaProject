package application;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;

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
			if(resultSet.next()) {
				gameName = resultSet.getString("GAME_NAME");
				producer = resultSet.getString("PRODUCER");
				publisher = resultSet.getString("PUBLISHER");
				released = resultSet.getString("RELEASED");
				platform = resultSet.getString("SNAME_PLATFORM");
				tableViewOrders.getItems().add(new Orders(gameName, producer, publisher, released, platform));
				while (resultSet.next()) {
					gameName = resultSet.getString("GAME_NAME");
					producer = resultSet.getString("PRODUCER");
					publisher = resultSet.getString("PUBLISHER");
					released = resultSet.getString("RELEASED");
					platform = resultSet.getString("SNAME_PLATFORM");
					tableViewOrders.getItems().add(new Orders(gameName, producer, publisher, released, platform));
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Zero products here", "History",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch (SQLException sq)
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to database", "Order Exception",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@FXML
	void zawowAction(Event event) throws SQLException {
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
				JOptionPane.showMessageDialog(null, "Ordered successfully", "Order",JOptionPane.INFORMATION_MESSAGE);
				((Node) (event.getSource())).getScene().getWindow().hide();
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
}