package application;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class emailException extends Exception {

    String name;
    TextField text;
    Label label;
    emailException(TextField text, Label label, String name)
    {
        this.name = name;
        this.label = label;
        this.text = text;
    }
    @Override
    public String getMessage() {
        text.setStyle("-fx-text-fill: red");
        return label.getText() +": "+name;
    }
}
