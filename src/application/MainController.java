package application;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private MenuItem logOutButton;
	@FXML
	private MenuItem closeButton;
	@FXML
	private MenuItem aboutButton;
	@FXML
	private Label aboutLabel;
	
	private LocalDate localDate;
	private LocalTime localTime;
	private LocalDateTime localDateTime;
	
	private XMLEncoder xmlencoder;
	
	private ObservableList <Integer> hourList = FXCollections.observableArrayList();
	private ObservableList <Integer> minuteList = FXCollections.observableArrayList();
	private ObservableList <CallendarEvent> eventList = FXCollections.observableArrayList();

	@FXML
	private ComboBox <Integer> hourBox;
	@FXML
	private ComboBox <Integer> minuteBox;
	@FXML 
	private TextField eventDescription;
	@FXML 
	private TextField eventVenue;
	@FXML
	private TextField errorTextField;
	@FXML
	private ListView <CallendarEvent> eventListView;
	@FXML
	private DatePicker calendar;

	
	public void logOut(ActionEvent event)
	{
		if (onLogOut() == true)
		{
			Stage stage = (Stage) calendar.getScene().getWindow();
			stage.close();
		}
		else return;
	}
	
	public void close(ActionEvent event)
	{
		System.exit(0);
	}
	
	public void onAboutClick(ActionEvent event)
	{
		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/AboutWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("LoginWindowStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();	
		} catch (IOException e) {
			errorTextField.setText(e.getMessage());
		}
			
	}
	
	public boolean onLogOut()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Logout confirmation");
		alert.setContentText("Are you sure you want to logout?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/LoginWindow.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("LoginWindowStyle.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();				
			} catch(Exception e) {
				e.printStackTrace();
			}
			finally {return true;}
		} else if (result.get() == ButtonType.CANCEL){
		    return false;
		}
		return true;
	}
	
	public void addEvent(ActionEvent event) throws NullDateException
	{
		if (calendar.getValue() == null){
			errorTextField.setText(new NullDateException().toString());
			return;
		}
		
		else
		{
			localDate = LocalDate.of(calendar.getValue().getYear(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getDayOfMonth());
			
			if (hourBox.getValue() == null && minuteBox.getValue() == null)
			{
				if (LocalTime.now().getHour() == 23) 
					localTime = LocalTime.of(0, 0);
				else
					localTime = LocalTime.of(LocalTime.now().getHour() + 1, 0);
				// plushour
			}

			else if (hourBox.getValue() == null)
				localTime = LocalTime.of(LocalTime.now().getHour() + 1, minuteBox.getValue());
			else if (minuteBox.getValue() == null)
				localTime = LocalTime.of(hourBox.getValue(), 0);
			else
				localTime = LocalTime.of(hourBox.getValue(), minuteBox.getValue());
			
			localDateTime = LocalDateTime.of(localDate, localTime);
			eventList.add(new CallendarEvent(localDateTime, eventDescription.getText(), eventVenue.getText()));
		}
	}
	
	public void serializeEvents(ActionEvent e)
	{
		try {
			xmlencoder = new XMLEncoder(new BufferedOutputStream(
			        new FileOutputStream("Events.xml")));
			xmlencoder.writeObject(eventList.get(0));
		} catch (FileNotFoundException e1) {
			errorTextField.setText("Serialization error");
		}
		finally
		{
			xmlencoder.close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i=0; i<=23; i++)
			hourList.add(Integer.valueOf(i));
		for (int i=0; i<=59; i++)
			minuteList.add(Integer.valueOf(i));

		hourBox.setItems(hourList);
		minuteBox.setItems(minuteList);
		eventListView.setItems(eventList);		
	}
}
