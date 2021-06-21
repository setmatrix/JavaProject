package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ModifyController extends data implements Initializable {

	public void initData(int id, String login, String email, String type) {
		txtId.setText(String.valueOf(id));
		txtLogin.setText(login);
		txtMail.setText(email);
		typeBox.setValue(type);
		typeBox.setVisible(true);
		typeLabel.setVisible(true);
		typeButton.setVisible(true);
		passwordButton.setVisible(false);
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
	private Button passwordButton;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtMail;

	@FXML
	private ComboBox<String> typeBox;

	@FXML
	private Button typeButton;

	@FXML
	private Label typeLabel;

	@FXML
	private Label labelEmail;

	@FXML
	void ChangeEmail() throws SQLException {
		Connection connection = null;
		String email = txtMail.getText();
		try {
			check_email(txtMail,labelEmail,email);
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
	void ChangeLogin(){
		String login = txtLogin.getText();
		try (Connection connection = getConnection()) {

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
		}

	}

	@FXML
	void ChangePassword() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("changePassword.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch (IOException sq)
		{
			JOptionPane.showMessageDialog(null,"Error with File","ChangePasswordFile",JOptionPane.ERROR_MESSAGE);
		}
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
		typeBox.setVisible(false);
		typeLabel.setVisible(false);
		typeButton.setVisible(false);
		passwordButton.setVisible(true);
	}
}