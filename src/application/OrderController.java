package application;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
				JOptionPane.showMessageDialog(null, "W tej chwili nie ma produktów", "Historia",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch (SQLException sq)
		{
			JOptionPane.showMessageDialog(null, "Nie można połączyć z bazą danych", "Problem",JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Zamówiono pomyślnie", "Zamówienia",JOptionPane.INFORMATION_MESSAGE);
				((Node) (event.getSource())).getScene().getWindow().hide();
			}
			catch (SQLException sq)
			{
				JOptionPane.showMessageDialog(null, "Problem z bazą danych", "Zamówienia", JOptionPane.ERROR_MESSAGE);
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
		//setColumn(tableViewOrders, "GAME_NAME");
		TableColumn<Orders, String> column = new TableColumn<>("Nazwa gry");
		column.setCellValueFactory(new PropertyValueFactory<>("GAME_NAME"));
		tableViewOrders.getColumns().add(column);
		column = new TableColumn<>("Producent");
		column.setCellValueFactory(new PropertyValueFactory<>("PRODUCER"));
		tableViewOrders.getColumns().add(column);
		column = new TableColumn<>("Wydawca");
		column.setCellValueFactory(new PropertyValueFactory<>("PUBLISHER"));
		tableViewOrders.getColumns().add(column);
		column = new TableColumn<>("Premiera");
		column.setCellValueFactory(new PropertyValueFactory<>("RELEASED"));
		tableViewOrders.getColumns().add(column);
		column = new TableColumn<>("Platforma");
		column.setCellValueFactory(new PropertyValueFactory<>("PLATFORM"));
		tableViewOrders.getColumns().add(column);
		//setColumn(tableViewOrders, "PRODUCER");
		//setColumn(tableViewOrders, "PUBLISHER");
		//setColumn(tableViewOrders, "RELEASED");
		//setColumn(tableViewOrders, "PLATFORM");
	}
}