package application;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {
	
	@FXML
	private TextField userNameLabel;
	@FXML
	private TextField passwordLabel;
	@FXML
	private TextField emailLabel;
	@FXML
	private Button backToLoginButton;
	@FXML 
	private Button submitButton;
	@FXML 
	private Label errorLabel;
	
	public void backToLoginWindow(ActionEvent event)
	{
		try {
			Stage stage = (Stage) backToLoginButton.getScene().getWindow();
			
			stage.close();
		} catch(Exception e) {
			e.printStackTrace();

		}	
	}
	
	public void submit(ActionEvent event)
	{
		UserDataBaseConnection conn = new UserDataBaseConnection();
		try {
			if(conn.connectRegister("Users.sqlite") != null){
				if (userNameLabel.getText().isEmpty() || passwordLabel.getText().isEmpty() || emailLabel.getText().isEmpty())
					throw new EmptyLabelException();
				else{
				String query = "INSERT INTO users(UserName, Password, Email) VALUES ('" +
						userNameLabel.getText() + "','" + passwordLabel.getText() + "','" + 
						emailLabel.getText() + "')";

				conn.stmt.getConnection().createStatement();
				conn.stmt.executeUpdate(query);
				}
			}
		} catch (FileNotFoundException e) {
			errorLabel.setText(e.getMessage());
		} catch (SQLException e) {
			errorLabel.setText(e.getMessage());
		} catch (EmptyLabelException e) {
			errorLabel.setText(e.toString());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameLabel.setText("");
		passwordLabel.setText("");
		emailLabel.setText("");
	}

}
