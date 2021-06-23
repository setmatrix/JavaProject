package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainSite extends Data implements Initializable {
    @FXML
    private Pane pane;

    protected Student localS;
    public void initData(Student s) {
        localS = s;
    }
    @FXML
    void homeAction() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Aplikacja.fxml")));
        pane.getChildren().setAll(root);
    }
    @FXML
    void orderAction() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("History.fxml")));
        pane.getChildren().setAll(root);
    }
    @FXML
    void settingsAction() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modify.fxml")));
        getNumberOfModify = true;
        pane.getChildren().setAll(root);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Aplikacja.fxml")));
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Initialize Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
