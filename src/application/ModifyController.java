package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModifyController {

    public void initData(int id, String login, String email, String type)
    {
        txtId.setText(String.valueOf(id));
        txtLogin.setText(login);
        txtMail.setText(email);
        typeBox.setValue(type);
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtMail;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    void ChangeEmail() {

    }

    @FXML
    void ChangeLogin() {

    }

    @FXML
    void ChangePassword() {

    }

    @FXML
    void ChangeType() {

    }

}
