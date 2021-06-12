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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AplikacjaController {

	@FXML
	private BorderPane rootPane;

	@FXML
	private Label welcome;

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
			Connection connection;
			int id = 0;
			String login = null, e_mail = null;
			try {
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					connection = DriverManager.getConnection(
							"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
				} else {
					connection = DriverManager
							.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
				}

				String sql = " SELECT * from Users "
						+ " WHERE NICK = ? AND E_MAIL = ?";

				PreparedStatement prestatement = connection.prepareStatement(sql);

				prestatement.setString(1, login);
				prestatement.setString(2, e_mail);

				ResultSet resultSet = prestatement.executeQuery();
				if (resultSet.next()) {
					sql = "Delete from [javaProject].[dbo].[Users] "
							+ "where ID_USER = " + (selectedIdx + 1);
					Statement stmt = connection.createStatement();
					ResultSet resultSet2 = stmt.executeQuery(sql);
					listView1.getItems().remove(selectedIdx);
					JOptionPane.showMessageDialog(null, "Success", "Application problem", JOptionPane.INFORMATION_MESSAGE);
					}
			} catch (SQLException sq) {
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Application problem", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Application problem", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void initialize() throws SQLException, UnknownHostException {
		listView1.setItems(listaUczniow);
		Connection connection;
		int id;
		String login, e_mail;
		welcome.setText("Welcome: " + User.login);
		try {
			if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
				connection = DriverManager.getConnection(
						"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");
			} else {
				connection = DriverManager
						.getConnection("jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
			}

		String sql = " SELECT ID_USER,NICk,E_MAIL from Users Where NICK != ? ";

			PreparedStatement prestatement = connection.prepareStatement(sql);

			prestatement.setString(1,User.login);

			ResultSet resultSet = prestatement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt("ID_USER");
				login = resultSet.getString("NICK");
				e_mail = resultSet.getString("E_MAIL");
				listaUczniow.add(new Student(id, login, e_mail));
			}
		}
		catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Application problem", JOptionPane.WARNING_MESSAGE);
			return;
		}

		catch (Exception se)
		{
			JOptionPane.showMessageDialog(null, se.getMessage(), "Application problem", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

}
