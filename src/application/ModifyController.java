package application;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class ModifyController {

	public void initData(int id, String login, String email, String type) {
		txtId.setText(String.valueOf(id));
		txtLogin.setText(login);
		txtMail.setText(email);
		typeBox.setValue(type);
		typeBox.getItems().setAll("Admin", "User");
		loginBase = login;
		emailBase = email;
		typeBase = type;
	}

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtMail;

	@FXML
	private ComboBox<String> typeBox;
	
	String loginBase,emailBase,typeBase;
	
	@FXML
	void AxceptAction(Event event) throws SQLException {

		int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log out", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (input == JOptionPane.YES_NO_OPTION) {
			Connection connection = null;
			String login = txtLogin.getText();
			String email = txtMail.getText();
			String type = typeBox.getValue();

			try {
				check_email(email);

				connection = connectionData();

				String sql = "UPDATE Users SET NICK = ?, E_MAIL = ?, ID_TYPE = ? WHERE ID_USER = ?";

				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setString(1, login);
					prestatement.setString(2, email);
					if (type.equals("Admin")) {
						prestatement.setString(3, "1");
					} else
						prestatement.setString(3, "2");

					prestatement.setInt(4, Integer.parseInt(txtId.getText()));
					prestatement.execute();

					JOptionPane.showMessageDialog(null, "Enail change - success", "Email information",
							JOptionPane.INFORMATION_MESSAGE);
					((Node) (event.getSource())).getScene().getWindow().hide();
				}
			} catch (SQLException sq) {
				JOptionPane.showMessageDialog(null, sq.getMessage(), "Database Problem", JOptionPane.ERROR_MESSAGE);
			} catch (Throwable th) {
				JOptionPane.showMessageDialog(null, th.getMessage(), "Data Exception", JOptionPane.WARNING_MESSAGE);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	@FXML
    void ExitAction(Event event) {
		if(!(txtLogin.getText().equals(loginBase)&&txtMail.getText().equals(emailBase)&&typeBox.getValue().equals(typeBase)))
    	{
    	int input = JOptionPane.showConfirmDialog(null,"Are you sure?","Exit Warning",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    	if (input == JOptionPane.YES_OPTION) {
    	((Node) (event.getSource())).getScene().getWindow().hide();
    	}
    	}
    }

	private void check_email(String mail) throws Throwable {
		int monkey = 0;
		int moncount = 0;
		int domainLength;
		if (mail.contains(".com")) {
			domainLength = 4;
		} else if (mail.contains(".pl")) {
			domainLength = 3;
		} else
			throw new Throwable(".pl or .com only");
		for (int i = 0; i < mail.length(); i++) {
			if (mail.charAt(i) == '@') {
				monkey = i;
				moncount += 1;
			}
		}
		if (moncount == 0) {
			throw new Throwable("Missing @");
		}
		if (moncount > 1) {
			throw new Throwable("Too much @");
		}
		if ((mail.substring(monkey).length() < domainLength + 3)) {
			throw new Throwable("Domain is not correct");
		}
		if (mail.substring(0, monkey).length() < 4) {
			throw new Throwable("Length before @ is short");
		}
	}

	Connection connectionData() throws SQLException, UnknownHostException {
		Connection connection = null;
		String dataLogin;
		String dataPass;
		String pc;
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

}
