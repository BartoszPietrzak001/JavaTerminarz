package application;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControl {
	@FXML
	private Label statusLabel;
	@FXML
	private TextField userNameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button loginButton;
	
	public void login(ActionEvent event)
	{
		UserDataBaseConnection conn = new UserDataBaseConnection();
		try {
			if (conn.connectLogin("Users.sqlite") != null){
				String query = "SELECT * FROM users WHERE UserName=" + userNameField.getText();
				conn.stmt.executeUpdate(query);
				if (userNameField.getText().equals("user") && passwordField.getText().equals("pass"))
				{
					statusLabel.setText("Login Success");
					loginSuccessed();			
					
					Stage stage = (Stage) loginButton.getScene().getWindow();
					
					stage.close();
				}
				else
				{
					statusLabel.setText("Login Failed");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loginSuccessed()
	{
			try {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/MainWindow.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("mainWindowStyle.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();				
			} catch(Exception e) {
				e.printStackTrace();

			}	
	}
	
	public void registrationWindow(ActionEvent event)
	{
		try {
			UserDataBaseConnection connection = new UserDataBaseConnection();
			if (connection.connectLogin("Users.sqlite") != null){
				try {
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/application/RegistrationWindow.fxml"));
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("registrationWindowStyle.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();				
				} catch(Exception e) {
					e.printStackTrace();		
				} 
			}
	
		} catch (FileNotFoundException e) {
			statusLabel.setText("Database not found");
		}
	}
}