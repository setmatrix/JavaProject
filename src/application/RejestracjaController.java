package application;

import java.net.InetAddress;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
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
	private Label warnmail;

	@FXML
	private Label warnnick;

	@FXML
	private Label warnpass;

    @FXML
    void powrotAction(ActionEvent event){
    	try
    	{
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		rootPane.setStyle("-fx-background-color:  #30C4CE;");

		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
    		
    	}

    }
	@FXML
	void zatwierdzAction(ActionEvent event) throws SQLException {
		boolean email_check = true;
		boolean nick_check = true;
		boolean pass_check = true;
		if(txtmail.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "email Field is empty", "Register Exception", JOptionPane.WARNING_MESSAGE);
			email_check = false;
		}
		if(loginText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "nick Field is empty", "Register Exception", JOptionPane.WARNING_MESSAGE);
			nick_check = false;
		}
		if(hasloText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "password Field is empty", "Register Exception", JOptionPane.WARNING_MESSAGE);
			pass_check = false;
		}
		if(email_check & pass_check & nick_check)
		{
			String mail = txtmail.getText();
			String pass = hasloText.getText();
			String nick = loginText.getText();
			Connection connection = null;
			try
			{
				check_email(mail);
				check_password(pass);
				login_check(nick);
				String pc;
				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject, sa, AlgorytmDjikstry";
				} else {
					pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject, sa, asdf";
				}
				connection = DriverManager.getConnection(pc);
				String sql = "INSERT INTO Users (E_MAIL, NICK, PASSWORD, ID_TYPE)"
						+ " VALUES (?,?,HASHBYTES(?,?),2);";

				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {

					prestatement.setString(1, mail);
					prestatement.setString(2, nick);
					prestatement.setString(3, "SHA1");
					prestatement.setString(4, pass);

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
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Register Exception", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Register Exception", JOptionPane.WARNING_MESSAGE);
			}
			finally {
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

	private void check_email(String mail) throws Exception
	{
		int monkey = 0;
		int moncount = 0;
		int domainlength;
		if(mail.contains(".com"))
		{
			domainlength = 4;
		}
		else if(mail.contains(".pl"))
		{
			domainlength = 3;
		} else throw new Exception(".pl or .com only");
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
			throw new Exception("Missing @");
		}
		if(moncount > 1)
		{
			throw new Exception("Too much @");
		}
		if((mail.substring(monkey).length() < domainlength +3))
		{
			throw new Exception("Domain is not correct");
		}
		if(mail.substring(0,monkey).length() < 4)
		{
			throw new Exception("Length before @ is short");
		}
	}

	private void check_password(String pass) throws Exception {
		if(pass.length() < 4) {
			throw new Exception("Password is too short");
		}
	}

	private void login_check(String login) throws Exception
	{
		if(login.length() < 3)
		{
			throw new Exception("Login is too short");
		}
	}
}
