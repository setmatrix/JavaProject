package application;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

public class OrderController implements Initializable {
	
	int loggedId;
	
	public void initData(Student s) {
		//this.loggedId = s.getId();
	}

	@FXML
	private BorderPane rootPane;

	@FXML
	ListView<Orders> listViewOrders = new ListView<>();
	ObservableList<Orders> listaOrders = FXCollections.observableArrayList();

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

	@FXML
	void ZawowAction() throws SQLException, UnknownHostException {
		String Order_name = null;
		String Order_date = null;
		int is_delivered = 0;
		Connection connection = null;
		try {
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
			connection = DriverManager.getConnection(pc, sqlLogin, sqlPass);
			String sql = " SELECT *" +
					 "from Orders " +
					 "INNER JOIN Users ON Users.ID_USER = Orders.CUSTOMER_ID " +
					 "WHERE ID_USER = ?";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				loggedId = 2;
				prestatement.setInt(1, loggedId);
				ResultSet resultSet = prestatement.executeQuery();
				while (resultSet.next()) {
				Order_name = resultSet.getString("ORDER_NAME");
				Order_date = resultSet.getString("ORDER_DATE");
				is_delivered = resultSet.getInt("IS_DELIVERED");
				}
				listaOrders.add(new Orders(Order_name, Order_date, is_delivered));
			}
		} catch (SQLException | NullPointerException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}



	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		listViewOrders.setItems(listaOrders);
	}

}