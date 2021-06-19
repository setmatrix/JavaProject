package application;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javax.swing.*;
import java.util.Objects;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Order.fxml")));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Own Application");
			primaryStage.show();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.WARNING_MESSAGE);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}