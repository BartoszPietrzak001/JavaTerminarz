package application;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {
	
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
				String query = "INSERT INTO users(UserName, Password, Email) VALUES ('" +
						userNameLabel.getText() + "','" + passwordLabel.getText() + "','" + 
						emailLabel.getText() + "')";
				conn.stmt.getConnection().createStatement();
				conn.stmt.executeUpdate(query);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
