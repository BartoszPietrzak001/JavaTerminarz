package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class EditController implements Initializable{
	
	@FXML
	private Button saveButton;
	@FXML
	private TextField dateLabel;
	@FXML
	private TextField descriptionLabel;
	@FXML
	private TextField venueLabel;
	
	private CallendarEvent event;
	
	public EditController(){
		
	}
	
	public void update(ActionEvent e){
		dateLabel.setText(event.getDateString());
		descriptionLabel.setText(event.getDescription());
		venueLabel.setText(event.getVenue());
		
		System.out.println(dateLabel.getText());
		System.out.println(descriptionLabel.getText());
		System.out.println(venueLabel.getText());
	}
	
	public void setText(CallendarEvent event){
		this.event = event;
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dateLabel = new TextField();
		descriptionLabel = new TextField();
		venueLabel = new TextField();
		
		dateLabel.setText("Initialize");
	}	
}
