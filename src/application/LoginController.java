package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.net.InetAddress;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

	@FXML
	private BorderPane rootPane;

	@FXML
	private TextField txtlogin;

    @FXML
    private PasswordField passwordBox;

	@FXML
	private Label warnmail;

	@FXML
	private Label warnpass;
    
    @FXML
    void logclick(ActionEvent event) throws Exception {

    	boolean email_check = true;
    	boolean pass_check = true;
    	if(txtlogin.getText().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "email is empty", "Login Exception", 0);
    		email_check = false;
    	}
    	if(passwordBox.getText().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "password is empty", "Login Exception", 0);
    		pass_check = false;
    	}
    	if(email_check & pass_check)
    	{
    		String login = txtlogin.getText();
    		String pass = passwordBox.getText();
    		
    		try
    		{
    			String sqllogin = null, sqlmail = null;
    			int sqlid;
				Connection connection;

				if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
					connection = DriverManager.getConnection(
							"jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject", "sa",
							"AlgorytmDjikstry");
				} else {
					connection = DriverManager.getConnection(
							"jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject", "sa", "asdf");
				}

				String sql = " SELECT * from Users "
							+" WHERE nick = ? AND password = HASHBYTES(?,?)";

				PreparedStatement prestatement = connection.prepareStatement(sql);

				prestatement.setString(1,login);
				prestatement.setString(2,"SHA1");
				prestatement.setString(3,pass);

				ResultSet resultSet = prestatement.executeQuery();
				if (resultSet.next()) {
					JOptionPane.showMessageDialog(null, "Login successful\nWelcome "+login+" ", "Login Information", 3);
					sqlid = resultSet.getInt("ID_USER");
					sqllogin = resultSet.getString("NICK");
					sqlmail = resultSet.getString("E_MAIL");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Aplikacja.fxml"));
					Parent root = (Parent) loader.load();

					AplikacjaController controller = loader.getController();
					controller.initData(new Student(sqlid, sqllogin, sqlmail));
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					((Node)(event.getSource())).getScene().getWindow().hide();
					stage.show();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect login or password", "Login Exception", 0);
				}
    		}
    		catch (SQLException sq)
			{
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Login Exception", 0);
			}
    		catch (Exception e)
    		{
    			JOptionPane.showMessageDialog(null, e.getMessage(), "Login Exception", 0);
    		}
    	}
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtlogin.focusedProperty().addListener(new ChangeListener<Boolean>() {
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
					if(txtlogin.getText().isEmpty())
					{
						warnmail.setText("E-mail Field is empty");
					}
					else if(txtlogin.getText().length() < 3)
					{
						warnmail.setText("E-mail is too short");
					}
				}
			}
		});
		passwordBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
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
					if(passwordBox.getText().isEmpty())
					{
						warnpass.setText("Password Field is empty");
					}
					else if(passwordBox.getText().length() < 3)
					{
						warnpass.setText("Password is too short");
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
		txtlogin.setTooltip(tool);
		tool = new Tooltip();
		tool.setText(
				"Your password must have:\n"+
				"at least 6 characters,\n"+
				"at least one Big and small letter,\n"+
				"at least one number"
		);
		passwordBox.setTooltip(tool);
	}
	@FXML
	void goBack(ActionEvent event) {
		try
		{
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Welcome.fxml"));
			//rootPane.setStyle("-fx-background-color:  #30C4CE;");

			rootPane.getChildren().setAll(root);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", 0);

		}
	}

}