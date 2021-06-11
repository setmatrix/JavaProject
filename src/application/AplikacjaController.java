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
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class AplikacjaController {

	@FXML
	private BorderPane rootPane;

	@FXML
	ListView<Student> listView1 = new ListView<>();

	ObservableList<Student> listaUczniow = FXCollections.observableArrayList();

	@FXML
	void actionModyfikacja(ActionEvent event) {
		final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
		System.out.println(selectedIdx);
	}

	@FXML
	void actionUsun(ActionEvent event) throws SQLException, UnknownHostException {
		final int selectedIdx = listView1.getSelectionModel().getSelectedIndex();
		  if (selectedIdx != -1) {
		  listView1.getItems().remove(selectedIdx);
		  }
		  Connection connection;
			String login=null, pass = null;
			if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
				connection = DriverManager.getConnection(
						"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
			} else {
				connection = DriverManager
						.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
			}

			String sql = " SELECT * from Users "
					+" WHERE nick = ? AND password = HASHBYTES(?,?)";

		//Statement prestatement = connection.prepareStatement(sql);

		//prestatement.setString(1,login);
		//prestatement.setString(2,"SHA1");
		//prestatement.setString(3,pass);

		//ResultSet resultSet = prestatement.executeQuery();
		//if (resultSet.next()) {
			
			//Statement statement = connection.createStatement();
			//Statement statement = connection.prepareStatement(sql);
			sql = "Delete from Users where ID="+1;
			//int deleteCount = statement.executeUpdate(sql);
			//sql = "Delete from Users where ID=?";
			PreparedStatement prestatement = connection.prepareStatement(sql);
			//prestatement.setString(1, "id");
			//System.out.println(sql);
			    prestatement.executeUpdate();
			    
			
			//prestatement.execute(sql);
		//}
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

	public void initialize() throws SQLException, UnknownHostException {
		listView1.setItems(listaUczniow);
		Connection connection;
		String login, haslo;
		if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
			connection = DriverManager.getConnection(
					"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
		} else {
			connection = DriverManager
					.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
		}

		String sql = " SELECT nick,password from Users ";
		try (Statement stmt = connection.createStatement()) {
			ResultSet resultSet = stmt.executeQuery(sql);
			// Statement statement = connection.prepareStatement(sql);

			// ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				login = resultSet.getString("nick");
				haslo = resultSet.getString("password");
				listaUczniow.add(new Student(login, haslo));
			}
		}
	}

}
