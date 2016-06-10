package application;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

	private StringBuilder stringBuilder;
	
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
	private TextField eventDate;
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
			eventDate.setEditable(false);
			localDate = LocalDate.of(calendar.getValue().getYear(), calendar.getValue().getMonthValue(), 
					calendar.getValue().getDayOfMonth());
			
			if (hourBox.getValue() == null && minuteBox.getValue() == null)
			{
					localTime = LocalTime.of(LocalTime.now().plusHours(1).getHour(), 0);
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
		XStream xstream = new XStream();
		
		try (FileWriter fileWriter = new FileWriter("Events.xml")) {
			for(CallendarEvent e1 : eventList){
				xstream.toXML(e1, fileWriter);
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}


//		try (PrintWriter pr = new PrintWriter("Events.xml");) {
//			pr.write(stringBuilder.toString());
//		} catch (FileNotFoundException e2) {
//			errorTextField.setText("Serialization error");
//		}
	}
	
	public void editEvent(ActionEvent e)
	{
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                   You've not chosen an event to edit!");
		else{
			CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
			String date = eventDate.getText();
			String description = eventDescription.getText();
			String venue = eventVenue.getText();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm.SSa"); 
//			LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
//			event.setDate(dateTime);
			
			event.setDescription(description);
			event.setVenue(venue);
		}
//
//		try {
//			CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
//			
//			Stage primaryStage = new Stage();
//
//			FXMLLoader fxmlLoader = new FXMLLoader();
//			fxmlLoader.setLocation(getClass().getResource("/application/EventEditWindow.fxml"));
//			
//			Parent root = fxmlLoader.load();
//			
//			
//			//System.out.println(controller.venueLabel.getText() + "jghj");
//			
//			EditController controller = fxmlLoader.getController();
//			fxmlLoader.setController(controller);
//			controller.setText(event);
//
//			
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("editWindowStyle.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	
	}
	
	public void deleteEvent(ActionEvent e){
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                   You've not chosen an event to delete!");
		else{
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		
		for (CallendarEvent calevent : eventList){
			if (calevent.equals(event)){
				eventList.remove(calevent);
				return;
				}
			}
		}
	}
	
	public void showEvent(MouseEvent e){
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		
		eventDate.setEditable(true);
		
		eventDate.setText(event.getDateString());
		eventDescription.setText(event.getDescription());
		eventVenue.setText(event.getVenue());
		
	}
	
	public void copyEvent(ActionEvent e){
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                  You've not chosen an event to copy!");
		else{
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		
		eventList.add(event);
		}
	}
	
	public void openSettingsWindow(ActionEvent e){

		try {
			Stage primaryStage = new Stage();
			Parent root;	
			root = FXMLLoader.load(getClass().getResource("/application/settingsWindow.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("settingsWindowStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		stringBuilder = new StringBuilder();
		}
	}
