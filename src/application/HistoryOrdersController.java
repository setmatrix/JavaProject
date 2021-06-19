package application;

import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HistoryOrdersController implements Initializable {
	
	int loggedId;
	
	public void initData(Student s) {
		this.loggedId = s.getId();
	}

	@FXML
	private BorderPane rootPane;

	@FXML
	ListView<HistoryOrders> listViewOrders = new ListView<>();
	ObservableList<HistoryOrders> listaOrders = FXCollections.observableArrayList();

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
	void LoadAction() throws SQLException, UnknownHostException {
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
			listViewOrders.getItems().clear();
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				loggedId = 2;
				prestatement.setInt(1, loggedId);
				ResultSet resultSet = prestatement.executeQuery();
				while (resultSet.next()) {
				Order_name = resultSet.getString("ORDER_NAME");
				Order_date = resultSet.getString("ORDER_DATE");
				is_delivered = resultSet.getInt("IS_DELIVERED");
				listaOrders.add(new HistoryOrders(Order_name, Order_date, is_delivered));
				}
				
			}
		} catch (SQLException | NullPointerException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@FXML
    void ZawowAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		Parent root = loader.load();
		OrderController controller = loader.getController();
		controller.initData(loggedId);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Poka¿ mi swoje towary");
		stage.show();
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		listViewOrders.setItems(listaOrders);
	}

}