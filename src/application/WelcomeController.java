package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class WelcomeController implements Initializable {

	@FXML
	private BorderPane rootPane;

	@FXML
	private ImageView image1;

	public ArrayList<Student> ListaUczniow = new ArrayList<Student>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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
		rootPane.setStyle("-fx-background-color:  lightBlue;");
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			rootPane.setStyle("-fx-background-color:  lightBlue;");

			rootPane.getChildren().setAll(root);


    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Login Window Exception", 0);

    	}
    }
    @FXML
    void rejestracjaAction(ActionEvent event) {
    	try
    	{
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Rejestracja.fxml"));
		rootPane.setStyle("-fx-background-color: lightBlue;");

		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", 0);

    	}
    }

    public static void rejestracjaUczia(String login, String haslo)
    {
    	//ListaUczniow.add(new Student(login,haslo));
    }
}
