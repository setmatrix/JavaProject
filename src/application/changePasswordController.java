package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class changePasswordController extends Data implements Initializable {

    private int id;
    @FXML
    private PasswordField txtOldPass;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private PasswordField txtConfirmPass;

    @FXML
    void changePassword(){
        try (Connection connection = getConnection()) {
            String sql = "Select * FROM Users WHERE ID_USER = ? AND PASSWORD = HASHBYTES(?,?)";
            try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
                prestatement.setInt(1, id);
                prestatement.setString(2, "SHA1");
                prestatement.setString(3, txtOldPass.getText());
                ResultSet set = prestatement.executeQuery();
                if (set.next()) {
                    if (txtNewPass.getText().equals(txtConfirmPass.getText())) {
                        String newPass = txtConfirmPass.getText();
                        check_password(txtConfirmPass,confirmPasswordLabel,newPass);
                        txtOldPass.setText("");
                        txtNewPass.setText("");
                        txtConfirmPass.setText("");
                        Connection newConnection = getConnection();
                        sql = "Update Users SET PASSWORD = HASHBYTES(?,?) WHERE ID_USER = ?";
                        try (PreparedStatement state = newConnection.prepareStatement(sql)) {
                            state.setString(1, "SHA1");
                            state.setString(2, newPass);
                            state.setInt(3, id);
                            state.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Password Changed", "Change", JOptionPane.INFORMATION_MESSAGE);
                            newConnection.close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Your new Password must be equal to Confirm", "Confirm Exception", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Your Password isn't correct", "Old Password Exception", JOptionPane.WARNING_MESSAGE);
                }
            } catch (emailException e) {
                JOptionPane.showMessageDialog(null, "Password incorrect", "Pass Exception", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException thr) {
            JOptionPane.showMessageDialog(null, "Password incorrect", "Pass Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id = st.getId();
    }
}
