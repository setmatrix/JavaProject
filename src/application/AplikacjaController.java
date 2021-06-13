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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;

import javafx.scene.layout.BorderPane;


public class AplikacjaController implements Initializable {

	@FXML
	private BorderPane rootPane;

	private String loggedlogin;

	@FXML
	private Label welcome;

	@FXML
	private Button del;

	@FXML
	private Button mody;

	@FXML
	private Button loadbutton;

	public void initData(Student s) {
		this.loggedlogin = s.getLogin();
		welcome.setText(loggedlogin);
	}

	@FXML
	ListView<Student> listView1 = new ListView<>();

	ObservableList<Student> listaUczniow = FXCollections.observableArrayList();

	@FXML
	void actionModyfikacja(ActionEvent event) {
		final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
	}

	@FXML
	void actionUsun(ActionEvent event) throws UnknownHostException, SQLException {
			final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
			Connection connection = null;
			if (selectedIdx != -1) {
				String pc;
				int id = 0;
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject, sa, AlgorytmDjikstry";
				} else {
					pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject, sa, asdf";
				}
				try {
					connection = DriverManager.getConnection(pc);
					String sql = "Delete from Users where NICK= ?";
					try (PreparedStatement prestatement = connection.prepareStatement(sql)){
						prestatement.setString(1, listView1.getSelectionModel().getSelectedItem().getLogin());
						prestatement.execute();
						listView1.getItems().remove(selectedIdx);
					}
				} catch (SQLException sq) {
					JOptionPane.showMessageDialog(null,sq.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
				}
					catch (NullPointerException nu)
					{
						JOptionPane.showMessageDialog(null,nu.getMessage(),"Warning",JOptionPane.WARNING_MESSAGE);
					}
				finally {
					if (connection != null) {
						connection.close();
					}
				}
			}
	}
	@FXML
	void powrotAction(ActionEvent event) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Welcome.fxml"));

			rootPane.getChildren().setAll(root);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", 0);
		}
	}

	@FXML
	void Load(ActionEvent event) throws SQLException {
		Connection connection = null;
		try {
			listView1.setItems(listaUczniow);
			int id;
			String login, email;
			String pc;
			if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
				pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject, sa, AlgorytmDjikstry";
			} else {
				pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject, sa, asdf";
			}
			connection = DriverManager.getConnection(pc);
			String sql = " SELECT ID_USER, NICK, E_MAIL from Users WHERE NICK != ?";
			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setString(1, loggedlogin);
				ResultSet resultSet = prestatement.executeQuery();
				while (resultSet.next()) {
					id = resultSet.getInt("ID_USER");
					login = resultSet.getString("NICK");
					email = resultSet.getString("E_MAIL");
					listaUczniow.add(new Student(id, login, email));
				}
				listView1.setDisable(false);
				del.setDisable(false);
				mody.setDisable(false);
				loadbutton.setDisable(true);
				loadbutton.setBackground(Background.EMPTY);
			}
		} catch (SQLException | UnknownHostException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Welcome Window Exception", 0);
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadbutton.setDisable(false);
	}
}
