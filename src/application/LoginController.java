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
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
public class LoginController implements Initializable {
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
    void logClick(ActionEvent event) throws IOException, SQLException {
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
    			String sqlFirstName;
    			String sqlLastName;
				String sqlNameType;

				String dataLogin;
				String dataPass;
				String pc;

				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
					dataLogin = "sa";
					dataPass= "AlgorytmDjikstry";
				} else {
					pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
					dataLogin = "sa";
					dataPass = "asdf";
				}
				connection = DriverManager.getConnection(pc, dataLogin, dataPass);

				String sql = " SELECT * "
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
						sqlFirstName = resultSet.getString("FIRST_NAME");
						sqlLastName = resultSet.getString("LAST_NAME");
						sqlNameType = resultSet.getString("ID_TYPE");
						FXMLLoader loader = new FXMLLoader(getClass().getResource("Aplikacja.fxml"));
						Parent root = loader.load();
						AplikacjaController controller = loader.getController();
						controller.initData(new Student(sqlId, sqlFirstName, sqlLastName, sqlLogin, sqlMail,  sqlNameType));
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.setTitle("Welcome " + sqlLogin.toUpperCase(Locale.ROOT));
						((Node) (event.getSource())).getScene().getWindow().hide();
						stage.show();
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect login or password", "Login", JOptionPane.WARNING_MESSAGE);
					}
				}
    		}
    		catch (SQLException | UnknownHostException sq)
			{
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Login Exception", JOptionPane.ERROR_MESSAGE);
			}
    		finally {
    			if(connection != null)
				{
					connection.close();
				}
			}
    	}
    }
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtLogin.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if (t1) {
				if (!warnMail.getText().isEmpty()) {
					warnMail.setText("");
				}
			} else {
				if (txtLogin.getText().isEmpty()) {
					warnMail.setText("Login Field is empty");
				} else if (txtLogin.getText().length() < 3) {
					warnMail.setText("Login is too short");
				}
			}
		});
		passwordBox.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnPass.getText().isEmpty())
				{
					warnPass.setText("");
				}
			}
			else
			{
				if(passwordBox.getText().isEmpty())
				{
					warnPass.setText("Password Field is empty");
				}
				else if(passwordBox.getText().length() < 3)
				{
					warnPass.setText("Password is too short");
				}
			}
		});
		Tooltip tool = new Tooltip();
		tool.setText(
				"""
						Your email must have:
						at least 8 characters in length
						 .pl or com
						 one @"""
		);
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
		}
	}
}
