package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class changePasswordController extends data implements Initializable {

    private int Id;
    @FXML
    private PasswordField txtOldPass;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private PasswordField txtConfirmPass;

    @FXML
    void changePassword(){
        try (Connection connection = getConnection()) {
            String sql = "Select * FROM Users WHERE ID_USER = ? AND PASSWORD = HASHBYTES(?,?)";
            try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
                prestatement.setInt(1, Id);
                prestatement.setString(2, "SHA1");
                prestatement.setString(3, txtOldPass.getText());
                ResultSet set = prestatement.executeQuery();
                if (set.next()) {
                    if (txtNewPass.getText().equals(txtConfirmPass.getText())) {
                        String newPass = txtConfirmPass.getText();
                        txtOldPass.setText("");
                        txtNewPass.setText("");
                        txtConfirmPass.setText("");
                        Connection newConnection = getConnection();
                        sql = "Update Users SET PASSWORD = HASHBYTES(?,?) WHERE ID_USER = ?";
                        try (PreparedStatement state = newConnection.prepareStatement(sql)) {
                            state.setString(1, "SHA1");
                            state.setString(2, newPass);
                            state.setInt(3, Id);
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
            }
        } catch (Throwable thr) {
            JOptionPane.showMessageDialog(null, "Problem with Database", "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id = st.getId();
    }
}
