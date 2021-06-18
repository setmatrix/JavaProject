package application;
import java.net.InetAddress;
import java.net.URL;
import java.util.Objects;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
public class RejestracjaController implements Initializable {
    @FXML
    private BorderPane rootPane;
	@FXML
	private TextField loginText;
    @FXML
    private PasswordField hasloText;
	@FXML
	private TextField txtmail;
	@FXML
	private TextField firstNameText;
    @FXML
	private TextField LastNameText;
	@FXML
	private Label warnmail;
	@FXML
	private Label warnnick;
	@FXML
	private Label warnpass;
    @FXML
    private Label warnfirstname;
    @FXML
    private Label warnlastname;
    @FXML
    void powrotAction(){
    	try
    	{
    	BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Welcome.fxml")));
		rootPane.setStyle("-fx-background-color:  #30C4CE;");

		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
    	}
    }
	@FXML
	 void zatwierdzAction() throws SQLException {
		boolean emailCheck = true;
		boolean nickCheck = true;
		boolean passCheck = true;
		if(txtmail.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "email Field is empty", "Register email Field", JOptionPane.WARNING_MESSAGE);
			emailCheck = false;
		}
		if(loginText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "nick Field is empty", "Register Login Field", JOptionPane.WARNING_MESSAGE);
			nickCheck = false;
		}
		if(hasloText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "password Field is empty", "Register password Field", JOptionPane.WARNING_MESSAGE);
			passCheck = false;
		}
		if(firstNameText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "firstname Field is empty", "Register firstname Field", JOptionPane.WARNING_MESSAGE);
			passCheck = false;
		}
		if(LastNameText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "lastname Field is empty", "Register lastname Field", JOptionPane.WARNING_MESSAGE);
			passCheck = false;
		}
		if(emailCheck && passCheck && nickCheck)
		{
			String mail = txtmail.getText();
			String pass = hasloText.getText();
			String nick = loginText.getText();
			String firstname = firstNameText.getText();
			String lastname = LastNameText.getText();

			Connection connection = null;
			try
			{
				check_email(mail);
				check_password(pass);
				login_check(nick);
				firstName_check(firstname);
				lastName_check(lastname);
				String sqlLogin;
				String sqlPass;
				String pc;
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
					sqlLogin = "sa";
					sqlPass= "AlgorytmDjikstry";
				} else {
					pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
					sqlLogin = "sa";
					sqlPass = "asdf";
				}
				connection = DriverManager.getConnection(pc, sqlLogin, sqlPass);
				String sql = "INSERT INTO Users (E_MAIL, NICK, PASSWORD, ID_TYPE, FIRST_NAME, LAST_NAME)"
						+ " VALUES (?,?,HASHBYTES(?,?),2,?,?);";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setString(1, mail);
					prestatement.setString(2, nick);
					prestatement.setString(3, "SHA1");
					prestatement.setString(4, pass);
					prestatement.setString(5, firstname);
					prestatement.setString(6, lastname);
					int rows = prestatement.executeUpdate();
					if (rows > 0) {
						JOptionPane.showMessageDialog(null, "Account was created. Enjoy!", "Register Information", JOptionPane.INFORMATION_MESSAGE);
						txtmail.setText("");
						hasloText.setText("");
						loginText.setText("");
					}
				}
			}
			catch (SQLException sq)
			{
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Register SQL Error", JOptionPane.ERROR_MESSAGE);
			} catch (Throwable throwable) {
				JOptionPane.showMessageDialog(null, throwable.getMessage(), "Register Error", JOptionPane.WARNING_MESSAGE);
			} finally {
				if (connection!= null) {
					connection.close();
				}
			}
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtmail.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnmail.getText().isEmpty())
				{
					warnmail.setText("");
				}
			}
			else
			{
				if(txtmail.getText().isEmpty())
				{
					warnmail.setText("E-mail Field is empty");
				}
				else if(txtmail.getText().length() < 3)
				{
					warnmail.setText("E-mail is too short");
				}
			}
		});
		loginText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnnick.getText().isEmpty())
				{
					warnnick.setText("");
				}
			}
			else
			{
				if(loginText.getText().isEmpty())
				{
					warnnick.setText("Login Field is empty");
				}
				else if(loginText.getText().length() < 3)
				{
					warnnick.setText("Login is too short");
				}
			}
		});
		hasloText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnpass.getText().isEmpty())
				{
					warnpass.setText("");
				}
			}
			else
			{
				if(hasloText.getText().isEmpty())
				{
					warnpass.setText("Password Field is empty");
				}
				else if(hasloText.getText().length() < 3)
				{
					warnpass.setText("Password Field is too short");
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
		txtmail.setTooltip(tool);
		tool = new Tooltip();
		tool.setText("Your nick must be at least 4 characters");
		loginText.setTooltip(tool);
		tool = new Tooltip();
		tool.setText(
				"""
						Your password must have:
						at least 6 characters,
						at least one Big and small letter,
						at least one number"""
		);
		hasloText.setTooltip(tool);
	}
	private void check_email(String mail) throws Throwable
	{
		int monkey = 0;
		int moncount = 0;
		int domainLength;
		if(mail.contains(".com"))
		{
			domainLength = 4;
		}
		else if(mail.contains(".pl"))
		{
			domainLength = 3;
		} else throw new Throwable(".pl or .com only");
		for(int i=0;i<mail.length(); i++)
		{
			if(mail.charAt(i) == '@')
			{
				monkey = i;
				moncount+=1;
			}
		}
		if(moncount == 0)
		{
			throw new Throwable("Missing @");
		}
		if(moncount > 1)
		{
			throw new Throwable("Too much @");
		}
		if((mail.substring(monkey).length() < domainLength +3))
		{
			throw new Throwable("Domain is not correct");
		}
		if(mail.substring(0,monkey).length() < 4)
		{
			throw new Throwable("Length before @ is short");
		}
	}
	private void check_password(String pass) throws Throwable {
		if(pass.length() < 4) {
			throw new Throwable("Password is too short");
		}
	}
	private void login_check(String login) throws Throwable
	{
		if(login.length() < 3)
		{
			throw new Throwable("Login is too short");
		}
	}
	private void firstName_check(String firstName) throws Throwable
	{
		if(firstName.charAt(0) < 64 || firstName.charAt(0) > 91)
		{
			throw new Throwable("FirstName must start with a capital letter ");
		}
		if(firstName.length()<3)
		{
			throw new Throwable("FirstName is too short");
		}
	}private void lastName_check(String lastName) throws Throwable
	{
		if(lastName.charAt(0) < 64 || lastName.charAt(0) > 91)
		{
			throw new Throwable("LastName must start with a capital letter ");
		}
		if(lastName.length() < 3)
		{
			throw new Throwable("LastName is too short");
		}
	}
}