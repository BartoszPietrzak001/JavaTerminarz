package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class settingsController {
	
	@FXML
	private RadioButton defaultRadioButton;
	@FXML 
	private RadioButton customRadioButton;
	@FXML
	private TextField pathTextField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button saveButton;
	
	public void save(ActionEvent e){
		
	}

}
