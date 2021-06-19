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

    @FXML
    private Label txtLogin;

    protected Student localS;

    private String loggedLogin;
    private int loggedId;
    private String loggedEmail;
    private String loggedType;

    public void initData(Student s) {
        localS = s;
        this.loggedLogin = localS.getLogin();
        this.loggedId = localS.getId();
        this.loggedEmail = localS.getEmail();
        this.loggedType = localS.getType();
        txtLogin.setText(loggedLogin);
    }


    @FXML
    void homeAction(ActionEvent event) throws IOException {
    }

    @FXML
    void orderAction(ActionEvent event) {

    }

    @FXML
    void settingsAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Aplikacja.fxml")));
            //AplikacjaController controller = new AplikacjaController();
            //controller.initData(new Student(loggedId,loggedLogin,loggedEmail,loggedType));
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
