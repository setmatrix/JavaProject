package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;


public class RejestracjaController implements Initializable {

    @FXML
    private BorderPane rootPane;
    
    @FXML
    private TextField LoginText;

    @FXML
    private PasswordField hasloText;

	@FXML
	private Button back;
    
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
String a="",b="";
    @FXML
    void zatwierdzAction(ActionEvent event) {
    	a=LoginText.getText();
    	b=hasloText.getText();
    	WelcomeController.rejestracjaUczia(a,b);
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}
}
