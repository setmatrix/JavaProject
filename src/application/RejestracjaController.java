package application;
import java.net.URL;
import java.util.Objects;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
public class RejestracjaController extends data implements Initializable {
	@FXML
	private BorderPane rootPane;

	@FXML
	private TextField loginText;

	@FXML
	private PasswordField hasloText;

	@FXML
	private TextField txtMail;

	@FXML
	private Label warnMail;

	@FXML
	private Label warnNick;

	@FXML
	private Label warnPass;

	@FXML
	private TextField firstNameText;

	@FXML
	private TextField LastNameText;

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtCode;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtNumber;

	@FXML
	private Label labelAddress;

	@FXML
	private Label labelPostalCode;

	@FXML
	private Label labelCity;

	@FXML
	private Label labelNumber;

	@FXML
	private Label labelMail;

	@FXML
	private Label loginLabel;

	@FXML
	private Label labelPassword;

	@FXML
	private Label labelFirstName;

	@FXML
	private Label labelLastName;

	@FXML
	private Label warnFirstName;

	@FXML
	private Label warnLastName;

	@FXML
	private Label warnCode;

	@FXML
	private Label warnAddress;

	@FXML
	private Label warnCity;

	@FXML
	private Label warnNumber;
    @FXML
    void powrotAction(){
    	try
    	{
    	BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Welcome.fxml")));
		rootPane.setStyle("-fx-background-color:  #30C4CE;");

		rootPane.getChildren().setAll(root);
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, e.getMessage(), "Welcome Window Exception", JOptionPane.ERROR_MESSAGE);
    	}
    }
	@FXML
	 void zatwierdzAction() throws SQLException, emailException {
		if(txtMail.getText().isEmpty() || loginText.getText().isEmpty() || hasloText.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Fill requires Fields", "Register email Field", JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			String mail = txtMail.getText();
			String pass = hasloText.getText();
			String nick = loginText.getText();
			String firstname = firstNameText.getText();
			String lastname = LastNameText.getText();

			String address = txtAddress.getText();
			String postal_Code = txtCode.getText();
			String city=txtCity.getText();
			String number=txtNumber.getText();

			Connection connection = null;
			try
			{
				check_email(txtMail,labelMail,mail);
				check_password(hasloText,labelPassword,pass);
				login_check(loginText,loginLabel,nick);
				word_check(firstNameText,labelFirstName,firstname, "First Name");
				word_check(LastNameText,labelLastName,lastname, "Last Name");
				address_check(txtAddress,labelAddress,address);
				postalCode_check(txtCode,labelPostalCode,postal_Code);
				word_check(txtCity,labelCity,city, "City");
				number_check(txtNumber,labelNumber,number);
				connection = getConnection();
				String sql = "INSERT INTO Users (E_MAIL, NICK, PASSWORD, ID_TYPE, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, CITY, Number)"
						+ " VALUES (?,?,HASHBYTES(?,?),2,?,?,?,?,?,?);";
				try (PreparedStatement prestatement = connection.prepareStatement(sql)) {
					prestatement.setString(1, mail);
					prestatement.setString(2, nick);
					prestatement.setString(3, "SHA1");
					prestatement.setString(4, pass);
					prestatement.setString(5, firstname);
					prestatement.setString(6, lastname);
					prestatement.setString(7,address);
					prestatement.setString(8,postal_Code);
					prestatement.setString(9,city);
					prestatement.setString(10,number);
					int rows = prestatement.executeUpdate();
					if (rows > 0) {
						JOptionPane.showMessageDialog(null, "Account was created. Enjoy!", "Register Information", JOptionPane.INFORMATION_MESSAGE);
						txtMail.setText("");
						hasloText.setText("");
						loginText.setText("");
						firstNameText.setText("");
						LastNameText.setText("");
						txtAddress.setText("");
						txtCode.setText("");
						txtCity.setText("");
						txtNumber.setText("");
					}
				}
			} catch(emailException | SQLException em) {
				JOptionPane.showMessageDialog(null, em.getMessage(), "Registration Exception", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (connection!= null) {
					connection.close();
				}
			}
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtMail.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnMail.getText().isEmpty())
				{
					warnMail.setText("");
				}
			}
			else
			{
				if(txtMail.getText().isEmpty())
				{
					warnMail.setText("E-mail Field is empty");
				}
				else if(txtMail.getText().length() < 3)
				{
					warnMail.setText("E-mail is too short");
				}
			}
		});
		loginText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnNick.getText().isEmpty())
				{
					warnNick.setText("");
				}
			}
			else
			{
				if(loginText.getText().isEmpty())
				{
					warnNick.setText("Login Field is empty");
				}
				else if(loginText.getText().length() < 3)
				{
					warnNick.setText("Login is too short");
				}
			}
		});
		hasloText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnPass.getText().isEmpty())
				{
					warnPass.setText("");
				}
			}
			else
			{
				if(hasloText.getText().isEmpty())
				{
					warnPass.setText("Password Field is empty");
				}
				else if(hasloText.getText().length() < 3)
				{
					warnPass.setText("Password Field is too short");
				}
			}
		});
		firstNameText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnFirstName.getText().isEmpty())
				{
					warnFirstName.setText("");
				}
			}
			else
			{
				if(firstNameText.getText().isEmpty())
				{
					warnFirstName.setText("FirstName Field is empty");
				}
				else if(firstNameText.getText().length() < 3)
				{
					warnFirstName.setText("First Name Field is too short");
				}
			}
		});
		LastNameText.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnLastName.getText().isEmpty())
				{
					warnLastName.setText("");
				}
			}
			else
			{
				if(LastNameText.getText().isEmpty())
				{
					warnLastName.setText("Last Name Field is empty");
				}
				else if(LastNameText.getText().length() < 3)
				{
					warnLastName.setText("Last Name Field is too short");
				}
			}
		});
		txtAddress.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnAddress.getText().isEmpty())
				{
					warnAddress.setText("");
				}
			}
			else
			{
				if(txtAddress.getText().isEmpty())
				{
					warnAddress.setText("Address Field is empty");
				}
				else if(txtAddress.getText().length() < 3)
				{
					warnAddress.setText("Address Field is too short");
				}
			}
		});
		txtCity.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnCity.getText().isEmpty())
				{
					warnCity.setText("");
				}
			}
			else
			{
				if(txtCity.getText().isEmpty())
				{
					warnCity.setText("City Field is empty");
				}
				else if(txtCity.getText().length() < 3)
				{
					warnCity.setText("City Field is too short");
				}
			}
		});
		txtCode.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnCode.getText().isEmpty())
				{
					warnCode.setText("");
				}
			}
			else
			{
				if(txtCode.getText().isEmpty())
				{
					warnCode.setText("Password Field is empty");
				}
				else if(txtCode.getText().length() < 3)
				{
					warnCode.setText("Password Field is too short");
				}
			}
		});
		txtNumber.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
			if(t1)
			{
				if(!warnNumber.getText().isEmpty())
				{
					warnNumber.setText("");
				}
			}
			else
			{
				if(txtNumber.getText().isEmpty())
				{
					warnNumber.setText("Number Field is empty");
				}
				else if(txtNumber.getText().length() < 3)
				{
					warnNumber.setText("Number Field is too short");
				}
			}
		});
		Tooltip tool = new Tooltip();
		tool.setText(
				"""
						Your email must have:
						at least 8 characters in length
						 .pl or com
						 one @"""
		);
		txtMail.setTooltip(tool);
		tool = new Tooltip();
		tool.setText("Your nick must be at least 4 characters");
		loginText.setTooltip(tool);
		tool = new Tooltip();
		tool.setText(
				"""
						Your password must have:
						at least 6 characters,
						at least one Big and small letter,
						at least one number"""
		);
		hasloText.setTooltip(tool);
	}
}