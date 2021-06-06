package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;


public class RejestracjaController implements Initializable {

    @FXML
    private BorderPane rootPane;

	@FXML
	private TextField loginText;

    @FXML
    private PasswordField hasloText;

	@FXML
	private Button back;

	@FXML
	private TextField txtmail;

	@FXML
	private Label warnmail;

	@FXML
	private Label warnnick;

	@FXML
	private Label warnpass;
    
    @FXML
    void powrotAction(ActionEvent event) throws IOException {
    	try
    	{
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Welcome.fxml"));
		rootPane.setStyle("-fx-background-color:  #30C4CE;");

		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", 0);
    		
    	}

    }
    @FXML
    void zatwierdzAction(ActionEvent event) {
		boolean email_check = true;
		boolean nick_check = true;
		boolean pass_check = true;
		if(txtmail.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "email Field is empty", "Register Exception", 0);
			email_check = false;
		}
		if(loginText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "nick Field is empty", "Register Exception", 0);
			nick_check = false;
		}
		if(hasloText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "password Field is empty", "Register Exception", 0);
			pass_check = false;
		}
		if(email_check & pass_check & nick_check)
		{
			String mail = txtmail.getText();
			String pass = hasloText.getText();
			String nick = loginText.getText();

			try
			{
				check_email(mail);
				check_password(pass);
				login_check(nick);

				Connection connection = DriverManager.getConnection("jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa", "AlgorytmDjikstry");

				Statement statement = connection.createStatement();

				ResultSet r = statement.executeQuery("SELECT COUNT(*) AS Count From Users");
				r.next();

				int id = r.getInt("Count");

				String sql = "INSERT INTO Users"
						+ " VALUES (?,?,?,?,'User');";

				PreparedStatement prestatement = connection.prepareStatement(sql);

				prestatement.setInt(1,id+1);
				prestatement.setString(2,mail);
				prestatement.setString(3,nick);
				prestatement.setString(4,pass);

				int rows = prestatement.executeUpdate();

				if(rows > 0)
				{
					JOptionPane.showMessageDialog(null, "Task failed successfully", "Register Information", 1);
				}

				connection.close();
			}
			catch (SQLException sq)
			{
				JOptionPane.showMessageDialog(null, "Problem with SQL", "Register Exception", 0);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), "Register Exception", 0);
			}
		}
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
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
			}
		});
		loginText.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
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
			}
		});
		hasloText.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
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
			}
		});
		Tooltip tool = new Tooltip();
		tool.setText(
				"Your email must have:\n" +
						"at least 8 characters in length\n" +
						" .pl or com\n"+
						" one @"
		);
		txtmail.setTooltip(tool);
		tool = new Tooltip();
		tool.setText("Your nick must be at least 4 characters");
		loginText.setTooltip(tool);
		tool = new Tooltip();
		tool.setText(
				"Your password must have:\n"+
						"at least 6 characters,\n"+
						"at least one Big and small letter,\n"+
						"at least one number"
		);
		hasloText.setTooltip(tool);
	}

	private void check_email(String mail) throws Exception
	{
		int monkey = 0;
		int moncount = 0;
		int domainlength = 0;
		boolean domain = false;
		if(mail.contains(".com"))
		{
			domain = true;
			domainlength = 4;
		}
		else if(mail.contains(".pl"))
		{
			domain = true;
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
		if((mail.substring(monkey, mail.length()).length() < domainlength +3))
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
