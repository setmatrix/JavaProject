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

public class ModifyController extends Data implements Initializable {

	public void initData(int id, String login, String email, String type) {
		txtId.setText(String.valueOf(id));
		txtLogin.setText(login);
		txtMail.setText(email);
		typeBox.setValue(type);
		typeBox.setVisible(true);
		typeLabel.setVisible(true);
		typeButton.setVisible(true);
		passwordButton.setVisible(false);
		readType();
	}

	private void readType() {
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
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, "Problem z wczytaniem baz danych", "Wczytywanie typów", JOptionPane.ERROR_MESSAGE);
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
	void changeEmail() throws SQLException {
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

				JOptionPane.showMessageDialog(null, "Zmiana e-mailu przebiegła pomyślnie", "E-mail", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (SQLException sq) {
				JOptionPane.showMessageDialog(null, "Problem ze zmianą e-maila", "E-mail", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, "Problem z bazą danych", "Baza danych", JOptionPane.ERROR_MESSAGE);
		} catch (emailException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Weryfikacja", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@FXML
	void changeLogin(){
		String login = txtLogin.getText();
		try (Connection connection = getConnection()) {

			String sql = "UPDATE Users SET NICK = ? WHERE ID_USER = ?";

			try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
				prestatement.setString(1, login);
				prestatement.setInt(2, Integer.parseInt(txtId.getText()));
				prestatement.execute();

				JOptionPane.showMessageDialog(null, "Pomyślnie zmieniono hasło.", "Login", JOptionPane.INFORMATION_MESSAGE);

			} catch (SQLException sq) {
				JOptionPane.showMessageDialog(null, "Użytkownik z loginem: " + login + " już istnieje", "Login", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, "Problem z bazą danych", "Database Problem", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void changePassword() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("changePassword.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch (IOException sq)
		{
			JOptionPane.showMessageDialog(null,"Nie znaleziono pliku ze zmianą hasła","Zmiana hasła",JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void changeType() {
		Connection connection;
		String type = typeBox.getValue();
		int idType = 0;
		try {
			connection = getConnection();

			String sql = "SELECT ID_TYPE FROM Type WHERE NAME_TYPE = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, type);
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					idType = resultSet.getInt("ID_TYPE");
				}
				sql = "UPDATE Users SET ID_TYPE = ? WHERE ID_USER = ?";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setInt(1, idType);
					prestatement.setInt(2, Integer.parseInt(txtId.getText()));
					prestatement.execute();

					JOptionPane.showMessageDialog(null, "Zmieniono typ konta", "Typ", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (SQLException sq) {
					JOptionPane.showMessageDialog(null, "Problem ze zmianą typu konta", "Typ", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException sq) {
			JOptionPane.showMessageDialog(null, "Problem z łącznością z bazą danych", "Type change", JOptionPane.ERROR_MESSAGE);
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