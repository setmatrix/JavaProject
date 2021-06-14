package application;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
public class AplikacjaController implements Initializable {

	private String loggedLogin;
	private int loggedId;
	private String loggedEmail;
	private String loggedType;

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
	}
	//
	@FXML
	ListView<Student> listView1 = new ListView<>();
	ObservableList<Student> listaUczniow = FXCollections.observableArrayList();
	@FXML
	void actionModyfikacja() {
	}
	@FXML
	void actionUsun() throws UnknownHostException, SQLException {
		final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
		//
		Connection connection = null;
		if (selectedIdx != -1) {
			String dataLogin;
			String dataPass;
			String pc;
			if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
				pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
				dataLogin = "sa";
				dataPass = "AlgorytmDjikstry";
			} else {
				pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
				dataLogin = "sa";
				dataPass = "asdf";
			}
			try {
				connection = DriverManager.getConnection(pc, dataLogin, dataPass);
				String sql = "Delete from Users where ID_USER= ?";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setInt(1, listView1.getSelectionModel().getSelectedItem().getId());
					prestatement.execute();
					listView1.getItems().remove(selectedIdx);
				}
			} catch (SQLException | NullPointerException sq) {
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadbutton.setDisable(false);
		listView1.setVisible(false);
		del.setVisible(false);
		mody.setVisible(false);
	}
	@FXML
	public void loadAction() throws SQLException {
		Connection connection = null;
		if(loggedType.equals("Admin")) {
			listView1.setVisible(true);
		}
		if(listView1.isVisible())
		{
			try {
				listView1.getItems().clear();
				listView1.setItems(listaUczniow);
				int id;
				String login;
				String email;
				String nameType;
				String dataLogin;
				String dataPass;
				String pc;
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
					dataLogin = "sa";
					dataPass = "AlgorytmDjikstry";
				} else {
					pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
					dataLogin = "sa";
					dataPass = "asdf";
				}
				connection = DriverManager.getConnection(pc, dataLogin, dataPass);

				String sql = " SELECT ID_USER, NICK, E_MAIL, NAME_TYPE " +
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
						listaUczniow.add(new Student(id, login, email, nameType));
					}
					del.setVisible(true);
					mody.setVisible(true);
					if (loadbutton.getText().equals("Load")) {
						loadbutton.setText("Refresh");
					}
				}
			} catch (SQLException | UnknownHostException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
			if(!loggedType.equals("Admin")) {
				loadbutton.setDisable(true);
			}
		}
	}
}