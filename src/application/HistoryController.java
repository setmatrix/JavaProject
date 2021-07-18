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

public class HistoryController extends Data implements Initializable {

    int loggedId;

    public void initData() {
        this.loggedId = st.getId();
    }

    @FXML
    private TableView<HistoryOrders> tableViewOrders;

    private void load(){
        int orderId;
        String orderName;
        String orderDate;
        int isDelivered;
        try (Connection connection = getConnection()) {
            String sql = " SELECT *" +
                    "from Orders " +
                    "INNER JOIN Users ON Users.ID_USER = Orders.CUSTOMER_ID " +
                    "WHERE ID_USER = ?";
            tableViewOrders.getItems().clear();
            try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
                prestatement.setInt(1, loggedId);
                ResultSet resultSet = prestatement.executeQuery();
                while (resultSet.next()) {
                    orderId = resultSet.getInt("ORDER_ID");
                    orderName = resultSet.getString("ORDER_NAME");
                    orderDate = resultSet.getString("ORDER_DATE");
                    isDelivered = resultSet.getInt("IS_DELIVERED");
                    tableViewOrders.getItems().add(new HistoryOrders(orderId, orderName, orderDate, isDelivered));
                }
            }
        } catch (SQLException sq) {
            JOptionPane.showMessageDialog(null, "Nie można się połączyć z bazą danych", "Problem z ładowaniem", JOptionPane.ERROR_MESSAGE);
        }
    }


    @FXML
    void loadAction() {
        load();
    }

    @FXML
    void zawowAction() throws IOException {
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
        tableViewOrders.getColumns().add(column);
        column = new TableColumn<>("Nazwa gry");
        column.setCellValueFactory(new PropertyValueFactory<>("orderName"));
        tableViewOrders.getColumns().add(column);
        column = new TableColumn<>("Nazwa zamówienia");
        column.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tableViewOrders.getColumns().add(column);
        column = new TableColumn<>("Czy dostarczono");
        column.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
        tableViewOrders.getColumns().add(column);

    }
}
