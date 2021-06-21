package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistoryOrdersController extends data implements Initializable {
	
	int loggedId;
	
	public void initData() {
		this.loggedId = st.getId();
	}

	@FXML
	private TableView<HistoryOrders> TableViewOrders;

	private void load(){
		int orderId;
		String Order_name;
		String Order_date;
		int is_delivered;
		try (Connection connection = getConnection()) {
			String sql = " SELECT *" +
					"from Orders " +
					"INNER JOIN Users ON Users.ID_USER = Orders.CUSTOMER_ID " +
					"WHERE ID_USER = ?";
			TableViewOrders.getItems().clear();
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setInt(1, loggedId);
				ResultSet resultSet = prestatement.executeQuery();
				while (resultSet.next()) {
					orderId = resultSet.getInt("ORDER_ID");
					Order_name = resultSet.getString("ORDER_NAME");
					Order_date = resultSet.getString("ORDER_DATE");
					is_delivered = resultSet.getInt("IS_DELIVERED");
					TableViewOrders.getItems().add(new HistoryOrders(orderId, Order_name, Order_date, is_delivered));
				}
			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Load Problem", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	@FXML
	void LoadAction() {
		load();
	}

	@FXML
    void ZawowAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		Parent root = loader.load();
		OrderController controller = loader.getController();
		controller.initData(loggedId);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Pokaz mi swoje towary");
		stage.show();
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setTable();
		initData();
		load();
	}
	private void setTable() {
		
		TableColumn<HistoryOrders, String> column;
		column = new TableColumn<>("ID");
		column.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("ORDER_NAME");
		column.setCellValueFactory(new PropertyValueFactory<>("orderName"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("ORDER_DATE");
		column.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		TableViewOrders.getColumns().add(column);
		column = new TableColumn<>("IS_DELIVERED");
		column.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
		TableViewOrders.getColumns().add(column);

	}
}