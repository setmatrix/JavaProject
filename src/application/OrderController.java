package application;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OrderController implements Initializable {

	int loggedId;

	public void initData(int loggedId) {
		this.loggedId = loggedId;
	}

	@FXML
	private BorderPane rootPane;

	@FXML
	private TableView<Orders> TableViewOrders;

	@FXML
	void powrotAction() {
		try {
			BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Welcome.fxml")));
			rootPane.setStyle("-fx-background-color:  #30C4CE;");

			rootPane.getChildren().setAll(root);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void load() throws SQLException, UnknownHostException {
		String GAME_NAME = null;
		String PRODUCER = null;
		String PUBLISHER = null;
		String RELEASED = null;

		Connection connection = dataload();

		String sql = " SELECT *" + "from Games ";
		try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = prestatement.executeQuery();
			while (resultSet.next()) {
				GAME_NAME = resultSet.getString("GAME_NAME");
				PRODUCER = resultSet.getString("PRODUCER");
				PUBLISHER = resultSet.getString("PUBLISHER");
				RELEASED = resultSet.getString("RELEASED");
				TableViewOrders.getItems().add(new Orders(GAME_NAME, PRODUCER, PUBLISHER, RELEASED));
			}

		}

	}

	@FXML
	void LoadAction() throws SQLException, UnknownHostException {
		load();
	}

	@FXML
	void ZawowAction() throws UnknownHostException, SQLException {
		if (TableViewOrders.getSelectionModel().getSelectedIndex() > -1) {
			Orders game = TableViewOrders.getSelectionModel().getSelectedItem();
			Connection connection = dataload();
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
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//listViewOrders.setItems(listaOrders);
		try {
			setTable();
			load();
		} catch (UnknownHostException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setTable() throws Throwable {
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

	}
	Connection dataload() throws SQLException, UnknownHostException {
		String sqlLogin;
		String sqlPass;
		String pc;
		if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
			pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
			sqlLogin = "sa";
			sqlPass = "AlgorytmDjikstry";
		} else {
			pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
			sqlLogin = "sa";
			sqlPass = "asdf";
		}
		return DriverManager.getConnection(pc, sqlLogin, sqlPass);

	}
	
}