package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainSite implements Initializable {
    @FXML
    private Pane pane;

    protected Student localS;


    public void initData(Student s) {
        localS = s;
    }


    @FXML
    void homeAction(ActionEvent event) throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Aplikacja.fxml")));
        pane.getChildren().setAll(root);
    }

    @FXML
    void orderAction(ActionEvent event) throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("History.fxml")));
        pane.getChildren().setAll(root);
    }

    @FXML
    void settingsAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Aplikacja.fxml")));
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
