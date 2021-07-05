package application;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
public class LoginController extends Data implements Initializable {
	@FXML
	private BorderPane rootPane;
	@FXML
	private TextField txtLogin;
	@FXML
	private PasswordField passwordBox;
	@FXML
	private Label warnMail;
	@FXML
	private Label warnPass;
	@FXML
	void logClick(ActionEvent event) throws SQLException {
		boolean emailCheck = true;
		boolean passCheck = true;
		if(txtLogin.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "email is empty", "Login Error", JOptionPane.WARNING_MESSAGE);
			emailCheck = false;
		}
		if(passwordBox.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "password is empty", "Password Error", JOptionPane.WARNING_MESSAGE);
			passCheck = false;
		}
		if(emailCheck && passCheck)
		{
			String login = txtLogin.getText();
			String pass = passwordBox.getText();
			Connection connection = null;
			try
			{
				String sqlLogin;
				String sqlMail;
				int sqlId;
				String sqlNameType;

				connection = getConnection();

				String sql = " SELECT ID_USER, E_MAIL, NICK, PASSWORD, NAME_TYPE "
						+"FROM Users "
						+ "INNER JOIN Type ON Users.ID_TYPE = Type.ID_TYPE "
						+" WHERE NICK = ? AND PASSWORD = HASHBYTES(?,?)";
				try(PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setString(1, login);
					prestatement.setString(2, "SHA1");
					prestatement.setString(3, pass);
					ResultSet resultSet = prestatement.executeQuery();
					if (resultSet.next()) {
						JOptionPane.showMessageDialog(null, "Login successful\nWelcome " + login + " ", "Success", JOptionPane.INFORMATION_MESSAGE);
						sqlId = resultSet.getInt("ID_USER");
						sqlLogin = resultSet.getString("NICK");
						sqlMail = resultSet.getString("E_MAIL");
						sqlNameType = resultSet.getString("NAME_TYPE");
						st = new Student(sqlId, sqlLogin, sqlMail, sqlNameType);
						FXMLLoader loader = new FXMLLoader(getClass().getResource("MainSite.fxml"));
						Parent root = loader.load();
						MainSite controller = loader.getController();
						controller.initData(new Student(sqlId, sqlLogin, sqlMail, sqlNameType));
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.setTitle("Welcome " + sqlLogin.toUpperCase(Locale.ROOT));
						((Node) (event.getSource())).getScene().getWindow().hide();
						stage.show();
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect login or password", "Login", JOptionPane.WARNING_MESSAGE);
					}
				}
			} catch (SQLException | IOException sq)
			{
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Login Exception", JOptionPane.ERROR_MESSAGE);
			} finally {
				if(connection != null)
				{
					connection.close();
				}
			}
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setListener(txtLogin,warnMail,"Login");
		setListener(passwordBox,warnPass,"Password");
		Tooltip tool = new Tooltip();
		tool.setText("Put your login here");
		txtLogin.setTooltip(tool);
		tool = new Tooltip();
		tool.setText(
				"""
						Your password must have:
						at least 6 characters,
						at least one Big and small letter,
						at least one number"""
		);
		passwordBox.setTooltip(tool);
	}
	@FXML
	void goBack() {
		try
		{
			BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Welcome.fxml")));
			rootPane.getChildren().setAll(root);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Login Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
}