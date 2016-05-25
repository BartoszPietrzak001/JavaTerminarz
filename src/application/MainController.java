package application;

import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private Button logOutButton;
	
	private int day, month, year;
	private Integer hour = null, minute = null;
	
	private ObservableList <Integer> dayList = FXCollections.observableArrayList();
	private ObservableList <Integer> monthList = FXCollections.observableArrayList();
	private ObservableList <Integer> yearList = FXCollections.observableArrayList();
	private ObservableList <Integer> hourList = FXCollections.observableArrayList();
	private ObservableList <Integer> minuteList = FXCollections.observableArrayList();
	private ObservableList <CallendarEvent> eventList = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox <Integer> dayBox;
	@FXML
	private ComboBox <Integer> monthBox;
	@FXML
	private ComboBox <Integer> yearBox;
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
		Stage stage = (Stage) logOutButton.getScene().getWindow();
		
		onLogOut();
		
		stage.close();
	}
	
	public void setDay(ActionEvent event){
		day = dayBox.getValue();
	}
	
	public void setMonth(ActionEvent event){
		month = monthBox.getValue();
	}
	
	public void setYear(ActionEvent event){
		year = yearBox.getValue();
	}
	
	public void setHour(ActionEvent event){
		hour = new Integer(hourBox.getValue());
	}
	
	public void setMinute(ActionEvent event){
		minute = new Integer(minuteBox.getValue());
	}
	
	public void onLogOut()
	{
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
	}
	
	public void addEvent(ActionEvent event) throws NullDateException
	{
		if (calendar.getValue() == null){
			errorTextField.setText(new NullDateException().toString());
			return;
		}
		else if (eventDescription.getText().equals("") && minute == null && hour == null && eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getYear()));
		
		else if (eventDescription.getText().equals("") && minute == null && eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getYear(), hour));
		
		else if (eventDescription.getText().equals("") && eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getYear(), hour, minute));
		
		else if (minute == null && hour == null && eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), eventDescription.getText()));
		
		else if (eventDescription.getText().equals(""))
			eventList.add(new CallendarEvent(eventVenue.getText(), calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear()));
		
		else if (minute == null && eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getYear(), hour, eventDescription.getText()));
		
		else if (minute == null && eventDescription.getText().equals(""))
			eventList.add(new CallendarEvent(eventVenue.getText(), calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), hour));
		
		else if (eventVenue.getText().equals("") && eventDescription.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), hour, minute));
		
		else if (eventVenue.getText().equals(""))
			eventList.add(new CallendarEvent(calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), hour, minute, eventDescription.getText()));
		
		else if (eventDescription.getText().equals(""))
			eventList.add(new CallendarEvent(eventVenue.getText(), calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), hour, minute));
		
		else
			eventList.add(new CallendarEvent(eventVenue.getText(), calendar.getValue().getDayOfMonth(), 
					calendar.getValue().getMonthValue(), calendar.getValue().getYear(), hour, minute, eventDescription.getText()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i=1; i<=31; i++)
			dayList.add(Integer.valueOf(i));
		for (int i=1; i<=12; i++)
			monthList.add(Integer.valueOf(i));
		for (int i=2016; i<=2050; i++)
			yearList.add(Integer.valueOf(i));
		for (int i=0; i<=23; i++)
			hourList.add(Integer.valueOf(i));
		for (int i=0; i<=59; i++)
			minuteList.add(Integer.valueOf(i));
		
		dayBox.setItems(dayList);
		monthBox.setItems(monthList);
		yearBox.setItems(yearList);
		hourBox.setItems(hourList);
		minuteBox.setItems(minuteList);
		eventListView.setItems(eventList);
		
	}
}
