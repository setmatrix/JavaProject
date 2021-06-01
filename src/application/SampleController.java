package application;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;

public class SampleController {

    @FXML
    private TextField txtmail;

    @FXML
    private PasswordField passwordBox;
    
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

}