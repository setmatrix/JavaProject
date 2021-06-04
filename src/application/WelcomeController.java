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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.*;

public class WelcomeController {

	@FXML
	private BorderPane rootPane;

	@FXML
	private ImageView image1;
	@FXML
	public void initialize()
	{
		String zrodlo = System.getProperty("user.dir")+"\\bin\\obrazek.png";
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(zrodlo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputstream);
		image1.setImage(image);
		//Daniel bóg
	}
    @FXML
    void login(ActionEvent event) throws IOException {
    	try
    	{
    		//FXMLLoader logon = new FXMLLoader(getClass().getResource("Sample.fxml"));
    		//Parent root = (Parent) logon.load();
    		//Stage stage = new Stage();
    		//stage.setTitle("Log in");
    		//stage.setScene(new Scene(root));
    		//((Node)event.getSource()).getScene().getWindow().hide();
    		//stage.show();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			rootPane.setStyle("-fx-background-color:  #30C4CE;");

			rootPane.getChildren().setAll(root);


    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Login Window Exception", 0);
    	}
    }
}
