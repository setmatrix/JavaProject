package application;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {
    protected static User st;

    protected static Connection getConnection() throws SQLException {
        try {
            String dataLogin;
            String dataPass;
            String pc;
            Connection connection;
            if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-HIQPTQP")) {
                pc = "jdbc:sqlserver://desktop-hiqptqp\\sqlexpress;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "AlgorytmDjikstry";
            } else {
                pc = "jdbc:sqlserver://DESKTOP-3SJ6CNC\\ASDF2019;databaseName=javaProject";
                dataLogin = "sa";
                dataPass = "asdf";
            }
            connection = DriverManager.getConnection(pc, dataLogin, dataPass);
            return connection;
        }
        catch (UnknownHostException | SQLException un)
        {
            throw new SQLException(un.getMessage());
        }
    }
    protected static void check_email(TextField text, Label label, String mail) throws emailException {
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
        } else throw new emailException(text,label," Wyłącznie .pl lub .com");
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
            throw new emailException(text,label," Brakuje @.");
        }
        if(moncount > 1)
        {
            throw new emailException(text,label," Za dużo @.");
        }
        if((mail.substring(monkey).length() < domainLength +3))
        {
            throw new emailException(text,label," Domena wpisana nieprawidłowo.");
        }
        if(mail.substring(0,monkey).length() < 4)
        {
            throw new emailException(text,label," Długość e-maila do @ jest za krótka.");
        }
    }
    protected void check_password(TextField text, Label label ,String pass) throws emailException {
        boolean smallLetter = false;
        boolean bigLetter = false;
        boolean isNumber = false;
        if(pass.length() < 6) {
            throw new emailException(text,label," Hasło jest za krótkie");
        }
        for(int i=0;i<pass.length();i++)
        {
            if(Character.isDigit(pass.charAt(i)))
            {
                isNumber = true;
            }
            if(Character.isLetter(pass.charAt(i)))
            {
                if(Character.isLowerCase(pass.charAt(i)))
                {
                    smallLetter = true;
                }
                else if(Character.isUpperCase(pass.charAt(i)))
                {
                    bigLetter = true;
                }
            }
        }
        if(!smallLetter || !bigLetter || !isNumber)
        {
            throw new emailException(text,label," Hasło nie przeszło weryfikacji.");
        }
    }
    protected void word_check(TextField text, Label label, String word, String com) throws emailException
    {
        if(Character.isLowerCase(word.charAt(0)))
        {
            throw new emailException(text,label,com + " Musi zacząc się z dużej litery ");
        }
        if(word.length() < 3)
        {
            throw new emailException(text, label, com + " jest za krótkie");
        }
    }
    protected void login_check(TextField text, Label label, String login) throws emailException
    {
        if(login.length() < 3)
        {
            throw new emailException(text,label," Login jest za krótki");
        }
    }
    protected void address_check(TextField text, Label label, String address) throws emailException
    {
        if(address.length() < 3)
        {
            throw new emailException(text,label," Adres jest za krótki.");
        }
    }
    protected void postalCode_check(TextField text, Label label, String postalCode) throws emailException
    {
        int count = 0;
        if(postalCode.length() < 5)
        {
            throw new emailException(text, label, " Kod pocztowy jest za krótki");
        }
        for(int i=0; i<postalCode.length(); i++) {
            if (postalCode.charAt(i) == '-') {
                count += 1;
                if (count > 1) {
                    throw new emailException(text, label, " Kod pocztowy wpisano nieprawidłowo");
                }
            }
            if (Character.isLetter(postalCode.charAt(i))) {
                throw new emailException(text, label, " Kod pocztowy ma wyłącznie cyfry oraz jeden minus");
            }
        }
    }
    protected void number_check(TextField text, Label label,String number) throws emailException
    {
        int i=0;
        if(number.length() < 8)
        {
            throw new emailException(text,label," Numer telefonu jest za krótki.");
        }
        if(number.charAt(0)=='+')
        {
            i = 1;
        }
        for(int j = i; j<number.length(); j++)
        {
            if(!Character.isDigit(number.charAt(j))) {
                throw new emailException(text,label," Numer jest zbudowany wyłącznie z liczb");
            }
        }
    }
    protected boolean getNumberOfModify;

    protected void setListener(TextField field, Label warnLabel, String text)
    {
        field.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1)
            {
                if(!warnLabel.getText().isEmpty())
                {
                    warnLabel.setText("");
                }
            }
            else
            {
                if(field.getText().isEmpty())
                {
                    warnLabel.setText(text + " Pole jest puste");
                }
                else if(field.getText().length() < 3)
                {
                    warnLabel.setText(text + " jest za krótkie");
                }
            }
        });
    }
    protected void setColumn(TableView<Orders> table, String text)
    {
        TableColumn<Orders, String> column = new TableColumn<>(text);
        column.setCellValueFactory(new PropertyValueFactory<>(text));
        table.getColumns().add(column);
    }
}
