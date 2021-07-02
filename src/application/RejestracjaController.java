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
public class RejestracjaController extends Data implements Initializable {
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
	private TextField lastNameText;

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
    	powrot();
    }
	@FXML
	 void zatwierdzAction() throws SQLException{
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
			String lastname = lastNameText.getText();

			String address = txtAddress.getText();
			String postalCode = txtCode.getText();
			String city=txtCity.getText();
			String number=txtNumber.getText();

			Connection connection = null;
			try
			{
				check_email(txtMail,labelMail,mail);
				check_password(hasloText,labelPassword,pass);
				login_check(loginText,loginLabel,nick);
				word_check(firstNameText,labelFirstName,firstname, "First Name");
				word_check(lastNameText,labelLastName,lastname, "Last Name");
				address_check(txtAddress,labelAddress,address);
				postalCode_check(txtCode,labelPostalCode,postalCode);
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
					prestatement.setString(8,postalCode);
					prestatement.setString(9,city);
					prestatement.setString(10,number);
					int rows = prestatement.executeUpdate();
					if (rows > 0) {
						JOptionPane.showMessageDialog(null, "Account was created. Enjoy!", "Register Information", JOptionPane.INFORMATION_MESSAGE);
						txtMail.setText("");
						hasloText.setText("");
						loginText.setText("");
						firstNameText.setText("");
						lastNameText.setText("");
						txtAddress.setText("");
						txtCode.setText("");
						txtCity.setText("");
						txtNumber.setText("");
						powrot();
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
    	setListener(txtMail,warnMail,"E-mail");
    	setListener(loginText,warnNick,"Login");
    	setListener(hasloText, warnPass, "Password");
    	setListener(firstNameText,warnFirstName, "First Name");
    	setListener(lastNameText,warnLastName, "Last Name");
    	setListener(txtAddress,warnAddress,"Address");
    	setListener(txtCity,warnCity,"City");
    	setListener(txtCode,warnCode,"Code");
    	setListener(txtNumber,warnNumber,"Number");
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
	private void powrot()
	{
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
}