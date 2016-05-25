package application;

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
	
}