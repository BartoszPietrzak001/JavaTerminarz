package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class settingsController implements Initializable {
	
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
	@FXML
	private ToggleGroup group;
	
	private String path;
	
	public void save(ActionEvent e){
		
	}
	
	public void setPathTextFieldTrue(MouseEvent e){
		pathTextField.setDisable(false);
	}
	
	public void setPathTextFieldFalse(MouseEvent e){
		pathTextField.setDisable(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		group = new ToggleGroup();
		
		defaultRadioButton.setToggleGroup(group);
		customRadioButton.setToggleGroup(group);
		
		defaultRadioButton.setDisable(false);
		
		pathTextField.setDisable(true);
	}

}
