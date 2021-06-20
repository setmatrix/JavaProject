package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.InetAddress;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ModifyController extends data implements Initializable {

	public void initData(int id, String login, String email, String type) {
		txtId.setText(String.valueOf(id));
		txtLogin.setText(login);
		txtMail.setText(email);
		typeBox.setValue(type);
		ReadType();
	}

	private void ReadType() {
		Connection connection;
		try {
			connection = getConnection();

			String sql = "SELECT * FROM Type";

			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				ResultSet resultSet = prestatement.executeQuery();
				String type;
				while (resultSet.next()) {
					type = resultSet.getString("NAME_TYPE");
					typeBox.getItems().add(type);
				}
			}
		} catch (Throwable throwable) {
			JOptionPane.showMessageDialog(null, throwable.getMessage(), "Read Problem", JOptionPane.ERROR_MESSAGE);
		}
	}


	@FXML
	private TextField txtId;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtMail;

	@FXML
	private ComboBox<String> typeBox;

	@FXML
	void ChangeEmail() throws SQLException {
		Connection connection = null;
		String email = txtMail.getText();
		try {
			check_email(email);
			connection = getConnection();

			String sql = "UPDATE Users SET E_MAIL = ? WHERE ID_USER = ?";

			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setString(1, email);
				prestatement.setInt(2, Integer.parseInt(txtId.getText()));
				prestatement.execute();

				JOptionPane.showMessageDialog(null, "Enail change - success", "Email information", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Database Problem", JOptionPane.ERROR_MESSAGE);
		} catch (Throwable th) {
			JOptionPane.showMessageDialog(null, th.getMessage(), "Email Exception", JOptionPane.WARNING_MESSAGE);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@FXML
	void ChangeLogin() throws SQLException {
		Connection connection = null;
		String login = txtLogin.getText();
		try {
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

			String sql = "UPDATE Users SET NICK = ? WHERE ID_USER = ?";

			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setString(1, login);
				prestatement.setInt(2, Integer.parseInt(txtId.getText()));
				prestatement.execute();

				JOptionPane.showMessageDialog(null, "Login change - success", "Login information", JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, sq.getMessage(), "Database Problem", JOptionPane.ERROR_MESSAGE);
		} catch (Throwable th) {
			JOptionPane.showMessageDialog(null, th.getMessage(), "Email Exception", JOptionPane.WARNING_MESSAGE);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}

	@FXML
	void ChangePassword() {

	}

	@FXML
	void ChangeType() {
		Connection connection;
		String type = typeBox.getValue();
		int id_type = 0;
		try {
			connection = getConnection();

			String sql = "SELECT ID_TYPE FROM Type WHERE NAME_TYPE = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, type);
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					id_type = resultSet.getInt("ID_TYPE");
				}
				sql = "UPDATE Users SET ID_TYPE = ? WHERE ID_USER = ?";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setInt(1, id_type);
					prestatement.setInt(2, Integer.parseInt(txtId.getText()));
					prestatement.execute();

					JOptionPane.showMessageDialog(null, "Type changed succesfully", "Type change", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (Throwable throwable) {
			JOptionPane.showMessageDialog(null, throwable.getMessage(), "Type change", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		initDataforUser();
	}

	private void initDataforUser()
	{
		txtId.setText(String.valueOf(st.getId()));
		txtLogin.setText(st.getLogin());
		txtMail.setText(st.getEmail());
		typeBox.setValue(st.getType());
		ReadType();
	}
}