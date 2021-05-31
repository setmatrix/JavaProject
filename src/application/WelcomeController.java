package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WelcomeController {
	
	
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
		System.out.println(zrodlo);
	}
}
