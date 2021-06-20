package application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class data {
    protected static Student st;

    protected static Connection getConnection() throws Throwable {
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
            throw new Throwable(un.getMessage());
        }
    }
    protected static void check_email(String mail) throws Throwable
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
    protected void check_password(String pass) throws Throwable {
        if(pass.length() < 4) {
            throw new Throwable("Password is too short");
        }
    }
    protected void word_check(String lastName, String word) throws Throwable
    {
        if(Character.isLowerCase(lastName.charAt(0)))
        {
            throw new Throwable(word + " must start with a capital letter ");
        }
        if(lastName.length() < 3)
        {
            throw new Throwable(word + " is too short");
        }
    }
    protected boolean getNumberOfModify;
}
