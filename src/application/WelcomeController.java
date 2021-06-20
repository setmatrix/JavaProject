package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
public class WelcomeController implements Initializable {
	@FXML
	private BorderPane rootPane;
	@FXML
	private ImageView image1;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		String pic = System.getProperty("user.dir")+"\\src\\pupil.png";
		FileInputStream input= null;
		try {
			input = new FileInputStream(pic);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.WARNING_MESSAGE);
		}
		assert input != null;
		Image image = new Image(input);
		image1.setImage(image);
	}
    @FXML
    void login() {
    	try
    	{
			BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
			rootPane.setStyle("-fx-background-color:  lightBlue;");
			rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Login Window Exception", JOptionPane.ERROR_MESSAGE);
    	}
    }
    @FXML
    void registerAction() {
    	try
    	{
    	BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rejestracja.fxml")));
		rootPane.setStyle("-fx-background-color: lightBlue;");
		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.WARNING_MESSAGE);
    	}
    }
}