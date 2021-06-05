package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

	@FXML
	private BorderPane rootPane;

    @FXML
    private TextField txtmail;

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
    	if(txtmail.getText().isEmpty())
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
    		String mail = txtmail.getText();
    		String pass = passwordBox.getText();
    		
    		try
    		{
    			check_email(mail);
        		Account acc = new Account(mail, pass);
    		}
    		catch (Exception e)
    		{
    			JOptionPane.showMessageDialog(null, e.getMessage(), "Login Exception", 0);
    		}
    	}
    }
	
    private void check_email(String mail) throws Exception
    {
    	int monkey = 0;
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
    	}
    	for(int i=0;i<mail.length(); i++)
    	{
    		if(mail.charAt(i) == '@')
    		{
    			monkey = i;
    		}
    	}
    	if(monkey == 0)
    	{
    		throw new Exception("Domain is not correct");
    	}
    	if((mail.substring(monkey, mail.length() - (mail.length() - monkey)).length() < 3))
    	{
    		throw new Exception("Domain is not correct");
    	}
    }

    private void check_password(String pass) throws Exception {
		if(pass.length() < 3) {
			throw new Exception("Password is too short");
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
						warnmail.setText("TextField is empty");
					}
					else if(txtmail.getText().length() < 3)
					{
						warnmail.setText("TextField is too short");
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
						warnpass.setText("Password Field is too short");
					}
				}
			}
		});
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