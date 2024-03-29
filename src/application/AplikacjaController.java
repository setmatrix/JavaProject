package application;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class AplikacjaController extends Data implements Initializable {

	private String loggedLogin;
	private int loggedId;
	private String loggedEmail;
	private String loggedType;

	private final ImageIcon infoIcon = new ImageIcon("src/info.png");

	@FXML
	private Label txtId;

	@FXML
	private Label txtLogin;

	@FXML
	private Label txtEmail;

	@FXML
	private Label txtType;

	@FXML
	private Button del;
	@FXML
	private Button mody;
	@FXML
	private Button loadbutton;

	public void initData(Student s) {
		this.loggedLogin = s.getLogin();
		this.loggedId = s.getId();
		this.loggedEmail = s.getEmail();
		this.loggedType = s.getType();
		txtEmail.setText("Email: "+loggedEmail);
		txtLogin.setText("Login: "+loggedLogin);
		txtId.setText("Id: "+loggedId);
		txtType.setText("Type: " + loggedType);
		if(!loggedType.equals("Admin"))
		{
			userList.setVisible(false);
			mody.setVisible(false);
			del.setVisible(false);
			loadbutton.setVisible(false);
		}
	}
	@FXML
	private TableView<Student> userList;

	@FXML
	void actionModyfikacja() throws IOException {
		if (userList.getSelectionModel().getSelectedIndex() > -1)
		{
			Student user = userList.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify.fxml"));
			Parent root = loader.load();
			ModifyController controller = loader.getController();
			controller.initData(user.getId(), user.getLogin(), user.getEmail(), user.getType());
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Modify: " + user.getId());
			stage.show();
		}
	}

	@FXML
	void actionUsun() throws SQLException {
		final int selectedIdx = userList.getSelectionModel().getSelectedIndex();
		Connection connection = null;
		if (selectedIdx != -1) {
			try {
				connection = getConnection();
				String sql = "Delete from Users where ID_USER= ?";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setInt(1, userList.getSelectionModel().getSelectedItem().getId());
					prestatement.execute();
					userList.getItems().remove(selectedIdx);
				}
			} catch (SQLException throwable) {
				JOptionPane.showMessageDialog(null, throwable.getMessage(), "Delete Problem", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
	@FXML
	public void loadAction() throws SQLException {
		listViewRefresh();
	}

	private void listViewRefresh() throws SQLException {
		Connection connection = null;
		if(userList.isVisible())
		{
			try {
				userList.getItems().clear();
				int id;
				String login;
				String email;
				String nameType;
				connection = getConnection();

				String sql = " SELECT * " +
						"from Users " +
						"INNER JOIN Type ON Users.ID_TYPE = Type.ID_TYPE " +
						"WHERE ID_USER != ?";

				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setInt(1, loggedId);
					ResultSet resultSet = prestatement.executeQuery();
					while (resultSet.next()) {
						id = resultSet.getInt("ID_USER");
						login = resultSet.getString("NICK");
						email = resultSet.getString("E_MAIL");
						nameType = resultSet.getString("NAME_TYPE");
						userList.getItems().add(new Student(id, login, email, nameType));
					}
					del.setVisible(true);
					mody.setVisible(true);
					if (loadbutton.getText().equals("Load")) {
						loadbutton.setText("Refresh");
					}
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
			}
				finally {
				if (connection != null) {
					connection.close();
				}
			}
			if(!loggedType.equals("Admin")) {
				loadbutton.setDisable(true);
			}
		}
	}

	@FXML
	void logOut(Event event) throws IOException {

		int input = JOptionPane.showConfirmDialog(null,"Are you sure?","Log out",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, infoIcon);

		if (input == JOptionPane.YES_NO_OPTION) {
			loggedId = 0;
			loggedLogin = null;
			loggedEmail = null;
			loggedType = null;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Welcome " + "Welcome".toUpperCase(Locale.ROOT));
			JOptionPane.showConfirmDialog(null,"Log out - success","Log out",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, infoIcon);
			((Node) (event.getSource())).getScene().getWindow().hide();
			stage.show();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			initData(st);
			setTable();
			listViewRefresh();
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null,sq.getMessage(), "Initialize Exception",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void setTable(){
		TableColumn<Student, String> column;
		column = new TableColumn<>("ID_USER");
		column.setCellValueFactory(new PropertyValueFactory<>("id"));
		userList.getColumns().add(column);
		column = new TableColumn<>("LOGIN");
		column.setCellValueFactory(new PropertyValueFactory<>("login"));
		userList.getColumns().add(column);
		column = new TableColumn<>("EMAIL");
		column.setCellValueFactory(new PropertyValueFactory<>("email"));
		userList.getColumns().add(column);
		column = new TableColumn<>("TYPE");
		column.setCellValueFactory(new PropertyValueFactory<>("type"));
		userList.getColumns().add(column);
	}
}