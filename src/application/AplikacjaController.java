package application;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class AplikacjaController implements Initializable {

	@FXML
	private BorderPane rootPane;

	private String loggedlogin;
	private String loggedE_mail;
	private int loggedId;


	@FXML
	private Label welcome;

	@FXML
	private Button del;

	@FXML
	private Button mody;

	@FXML
	private Button loadbutton;

	private static Student student;

	public void initData(Student s) {
		this.student = s;
		this.loggedE_mail = s.getE_mail();
		this.loggedlogin = s.getLogin();
		this.loggedId = s.getId();
		welcome.setText(loggedlogin);
	}

	@FXML
	ListView<Student> listView1 = new ListView<>();

	ObservableList<Student> listaUczniow = FXCollections.observableArrayList();

	@FXML
	void actionModyfikacja(ActionEvent event) {
		final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
		System.out.println(selectedIdx);
	}

	@FXML
	void actionUsun(ActionEvent event){
		try {
			final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
			if (selectedIdx != -1) {
				Connection connection;
				String login = null, e_mail = null;
				int id = 0;
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					connection = DriverManager.getConnection(
							"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
				} else {
					connection = DriverManager
							.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
				}
				String sql = "Delete from Users where NICK= ?";

				PreparedStatement prestatement = connection.prepareStatement(sql);
				prestatement.setString(1,listView1.getSelectionModel().getSelectedItem().getLogin());
				prestatement.execute();

				prestatement.close();

				connection.close();

				listView1.getItems().remove(selectedIdx);

			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
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
	void Load(ActionEvent event) { ;
		try {
			listView1.setItems(listaUczniow);
			Connection connection;
			int id = 0;
			String login = null, e_mail = null;
			if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
				connection = DriverManager.getConnection(
						"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
			} else {
				connection = DriverManager
						.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
			}
			String sql = " SELECT ID_USER, NICK, E_MAIL from Users WHERE NICK != ?";
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, loggedlogin);
			ResultSet resultSet = prestatement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID_USER");
				login = resultSet.getString("NICK");
				e_mail = resultSet.getString("E_MAIL");
				listaUczniow.add(new Student(id, login, e_mail));
			}
			listView1.setDisable(false);
			del.setDisable(false);
			mody.setDisable(false);
			loadbutton.setDisable(true);
			loadbutton.setBackground(Background.EMPTY);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadbutton.setDisable(false);
	}
}
