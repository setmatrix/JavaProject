package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class ModifyController {

    public void initData(int id, String login, String email, String type)
    {
        txtId.setText(String.valueOf(id));
        txtLogin.setText(login);
        txtMail.setText(email);
        typeBox.setValue(type);
        typeBox.getItems().setAll("Admin", "User");
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
    void ChangeEmail() throws SQLException {
        Connection connection = null;
        String email = txtMail.getText();
        try {
            check_email(email);
            String dataLogin;
            String dataPass;
            String pc;
            if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
                pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
                dataLogin = "sa";
                dataPass= "AlgorytmDjikstry";
            } else {
                pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "asdf";
            }
            connection = DriverManager.getConnection(pc, dataLogin, dataPass);

            String sql = "UPDATE Users SET E_MAIL = ? WHERE ID_USER = ?";

            try(PreparedStatement prestatement = connection.prepareStatement(sql)) {
                prestatement.setString(1,email);
                prestatement.setInt(2, Integer.parseInt(txtId.getText()));
                prestatement.execute();

                JOptionPane.showMessageDialog(null,"Enail change - success", "Email information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException sq)
        {
            JOptionPane.showMessageDialog(null,sq.getMessage(), "Database Problem", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable th)
        {
            JOptionPane.showMessageDialog(null,th.getMessage(), "Email Exception", JOptionPane.WARNING_MESSAGE);
        }
        finally {
            if(connection!=null)
            {
                connection.close();
            }
        }
    }

    @FXML
    void ChangeLogin() throws SQLException {
        Connection connection = null;
        String login = txtLogin.getText();
        try {
            String dataLogin;
            String dataPass;
            String pc;
            if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
                pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
                dataLogin = "sa";
                dataPass= "AlgorytmDjikstry";
            } else {
                pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "asdf";
            }
            connection = DriverManager.getConnection(pc, dataLogin, dataPass);

            String sql = "UPDATE Users SET NICK = ? WHERE ID_USER = ?";

            try(PreparedStatement prestatement = connection.prepareStatement(sql)) {
                prestatement.setString(1,login);
                prestatement.setInt(2, Integer.parseInt(txtId.getText()));
                prestatement.execute();

                JOptionPane.showMessageDialog(null,"Login change - success", "Login information", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException sq)
        {
            JOptionPane.showMessageDialog(null,sq.getMessage(), "Database Problem", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable th)
        {
            JOptionPane.showMessageDialog(null,th.getMessage(), "Email Exception", JOptionPane.WARNING_MESSAGE);
        }
        finally {
            if(connection!=null)
            {
                connection.close();
            }
        }

    }

    @FXML
    void ChangePassword() {

    }

    @FXML
    void ChangeType() {
        Connection connection = null;
        String type = typeBox.getValue();
        String dataLogin;
        String dataPass;
        String pc;
        int id_type = 0;
        try
        {
            if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
                pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "AlgorytmDjikstry";
            } else {
                pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "asdf";
            }
            connection = DriverManager.getConnection(pc,dataLogin,dataPass);

            String sql = "SELECT ID_TYPE FROM Type WHERE NAME_TYPE = ?";

            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1,type);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next())
                {
                    id_type = resultSet.getInt("ID_TYPE");
                }
                sql = "UPDATE Users SET ID_TYPE = ? WHERE ID_USER = ?";
                try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
                    prestatement.setInt(1,id_type);
                    prestatement.setInt(2, Integer.parseInt(txtId.getText()));
                    prestatement.execute();

                    JOptionPane.showMessageDialog(null, "Type changed succesfully", "Type change", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (UnknownHostException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void check_email(String mail) throws Throwable
    {
        int monkey = 0;
        int moncount = 0;
        int domainLength;
        if(mail.contains(".com"))
        {
            domainLength = 4;
        }
        else if(mail.contains(".pl"))
        {
            domainLength = 3;
        } else throw new Throwable(".pl or .com only");
        for(int i=0;i<mail.length(); i++)
        {
            if(mail.charAt(i) == '@')
            {
                monkey = i;
                moncount+=1;
            }
        }
        if(moncount == 0)
        {
            throw new Throwable("Missing @");
        }
        if(moncount > 1)
        {
            throw new Throwable("Too much @");
        }
        if((mail.substring(monkey).length() < domainLength +3))
        {
            throw new Throwable("Domain is not correct");
        }
        if(mail.substring(0,monkey).length() < 4)
        {
            throw new Throwable("Length before @ is short");
        }
    }

}
