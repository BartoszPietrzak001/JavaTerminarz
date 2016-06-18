package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SettingsController implements Initializable {
	
	@FXML
	private RadioButton defaultRadioButton;
	@FXML 
	private RadioButton customRadioButton;
	@FXML
	private Button pathChooser;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button saveButton;
	@FXML
	private ToggleGroup group;
	@FXML 
	private TextField pathTextField;
	
	private String path;
	
	public void save(ActionEvent e){
		if (defaultRadioButton.disabledProperty().equals(true))
			ApplicationSettings.setFilePath("Users.sqlite");
		else
			ApplicationSettings.setFilePath(path);
		
		ApplicationSettings.setDate(datePicker.getValue());
		
		SettingsClass settings = new SettingsClass(ApplicationSettings.getFilePath(), ApplicationSettings.getDefaultCustom(), ApplicationSettings.getDate());
		
		XStream stream = new XStream();
		
		try (FileWriter writer = new FileWriter("settings.xml")){
			writer.write("");
			stream.toXML(settings, writer);
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void setPathTextFieldTrue(MouseEvent e){
		pathChooser.setDisable(false);
		ApplicationSettings.setDefaultCustom(false);
	}
	
	public void setPathTextFieldFalse(MouseEvent e){
		pathChooser.setDisable(true);
		ApplicationSettings.setDefaultCustom(true);
	}
	
	public void chooseDataBasePath(ActionEvent e){
		FileChooser fc = new FileChooser();
		File selectedFile;
		fc.setInitialDirectory(new File("C:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("SQLITE files (*.sqlite)", "*.sqlite"));
		
		fc.setTitle("Choose an XML file");				
		selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null){
			path = selectedFile.getAbsolutePath();
			pathTextField.setText(selectedFile.getAbsolutePath());
		}
	}
	
	public static void load(){
		XStream stream = new XStream();
		SettingsClass loadedSettings = new SettingsClass();
		
		try (FileReader reader = new FileReader("settings.xml")){
			loadedSettings = (SettingsClass) stream.fromXML(reader);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (loadedSettings.getInnerLocalDate() == null) return;
		else
			ApplicationSettings.setDate(loadedSettings.getInnerLocalDate());
		if (loadedSettings.getInnerString() == null) return;
		else{
			ApplicationSettings.setFilePath(loadedSettings.getInnerString());
			ApplicationSettings.setDefaultCustom(loadedSettings.getInnerDefaultCustom());
		}
			
}
	
	private static class SettingsClass{
		private String innerString = null;
		private boolean innerDefaultCustom;
		private LocalDate innerLocalDate = null;
		
		public SettingsClass(String s, boolean b, LocalDate d){
			innerString = s;
			innerDefaultCustom = b;
			innerLocalDate = d;
		}
		
		public SettingsClass(){}
		
		private String getInnerString(){
			return innerString;
		}
		
		private boolean getInnerDefaultCustom(){
			return innerDefaultCustom;
		}
		
		private LocalDate getInnerLocalDate(){
			return innerLocalDate;
		}
		
		private void setInnerLocalDate(LocalDate date){
			innerLocalDate = date;
		}
		
		private void setInnerDefaultCustom(boolean dc){
			innerDefaultCustom = dc;
		}
		
		private void setInnerString(String str){
			innerString = str;
		}
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		group = new ToggleGroup();
		
		defaultRadioButton.setToggleGroup(group);
		customRadioButton.setToggleGroup(group);
		
		
		defaultRadioButton.setDisable(ApplicationSettings.getDefaultCustom());
		
		pathChooser.setDisable(ApplicationSettings.getDefaultCustom());
		
		pathTextField.setText(ApplicationSettings.getFilePath());
	}

}
