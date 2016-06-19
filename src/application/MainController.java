package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;

import application.AlertBoxes.AlertBoxType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController implements Initializable {

	
	private LocalDate localDate;
	private LocalTime localTime;
	private LocalDateTime localDateTime;

	private StringBuilder stringBuilder;
	
	private String filePath;
	
	private ObservableList <Integer> hourList = FXCollections.observableArrayList();
	private ObservableList <Integer> minuteList = FXCollections.observableArrayList();
	private ObservableList <CallendarEvent> eventList = FXCollections.observableArrayList();

	@FXML
	private MenuItem logOutButton;
	@FXML
	private MenuItem closeButton;
	@FXML
	private MenuItem aboutButton;
	@FXML
	private Label aboutLabel;
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
	@FXML
	private Button undoButton;
	
	private static enum undoType{
		add, delete, edit, copy
	}
	
	private static enum fileChooserType{
		save, open
	}
	
	public fileChooserType getFileChooserTypeSave(){
		return fileChooserType.save;
	}
	
	private static undoType undoType;
	
	private CallendarEvent buffer;
	private CallendarEvent editBuffer;

	public void showEvent(MouseEvent e){
		if (eventListView.getSelectionModel().getSelectedItem() == null) return;
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		
		eventDate.setEditable(true);
		
		eventDate.setText(event.getDateString());
		eventDescription.setText(event.getDescription());
		eventVenue.setText(event.getVenue());
		
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
			undoButton.setDisable(false);
			undoType = undoType.add;
		}
	}
	
	public void editEvent(ActionEvent e)
	{
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                   You've not chosen an event to edit!");
		else{
			stringBuilder.setLength(0);
			CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
			stringBuilder.append(eventDate.getText());
			
//			if(hourBox.getValue() != null && minuteBox.getValue() !=null){
//				stringBuilder.append(hourBox.getValue().toString());
//			}
			String description = eventDescription.getText();
			String venue = eventVenue.getText();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"); 
//			LocalDateTime dateTime = LocalDateTime.parse(stringBuilder.toString(), formatter);
//			event.setDate(dateTime);
			
			event.setDescription(description);
			event.setVenue(venue);
			undoType = undoType.edit;
			undoButton.setDisable(false);
		}
	}
	
	public void deleteEvent(ActionEvent e){
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                   You've not chosen an event to delete!");
		else{
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		
		for (CallendarEvent calevent : eventList){
			if (calevent.equals(event)){
				eventList.remove(calevent);
				undoType = undoType.delete;
				undoButton.setDisable(false);
				buffer = new CallendarEvent(calevent);
				return;
				}
			}
		}
	}
	
	public void copyEvent(ActionEvent e){
		if(eventListView.getSelectionModel().getSelectedItem() == null)
			errorTextField.setText("                  You've not chosen an event to copy!");
		else{
		CallendarEvent event = eventListView.getSelectionModel().getSelectedItem();
		undoType = undoType.copy;
		undoButton.setDisable(false);
		eventList.add(event);
		}
	}
	
	public void undo(ActionEvent e)
	{
		switch(undoType)
		{
			case add:
			case copy:
			{
				eventList.remove(eventList.size()-1);
				break;
			}
			case delete:
			{
				eventList.add(buffer);
				break;
			}
			case edit:
			{
				eventList.remove(editBuffer);
				eventList.add(buffer);
				break;
			}
		}
		undoButton.setDisable(true);
	}
	
	public void calendarOnMouseExited(ActionEvent e){
		stringBuilder.setLength(0);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		stringBuilder.append(calendar.getValue().format(formatter));
		eventDate.setText(stringBuilder.toString());
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
	
	public void saveEvents(ActionEvent e){
		UserDataBaseConnection connection = new UserDataBaseConnection();
		Connection conn;
		
		if (connection.saveEventsConnection(ApplicationSettings.getFilePath()) != null){
			conn = connection.saveEventsConnection(ApplicationSettings.getFilePath());
			StringBuilder strBuilder = new StringBuilder();
			String query = new String("INSERT INTO Events (EventDate, EventDescription, EventVenue, UserName)"
					+ "VALUES(");
			
			for (CallendarEvent event : eventList){
				strBuilder.setLength(0);
				strBuilder.append(query + "'" + event.getDateString() + "', '" + event.getDescription() + "', '" + 
								  event.getVenue() + "', '" + ApplicationSettings.getLogin() + "');d");
				
				try {
					connection.stmt = conn.createStatement();
					connection.stmt.execute(strBuilder.toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
	}
	
	public void loadEvents(){
		UserDataBaseConnection connection = new UserDataBaseConnection();
		Connection conn;
		ResultSet rs;
		CallendarEvent event;
		
		if (connection.loadEventsConnection(ApplicationSettings.getFilePath()) != null){
			conn = connection.saveEventsConnection(ApplicationSettings.getFilePath());
			StringBuilder strBuilder = new StringBuilder();
			
			strBuilder.append("SELECT * FROM EVENTS WHERE UserName = '" + ApplicationSettings.getLogin() + "'");
			
			try {
				rs = connection.stmt.executeQuery(strBuilder.toString());
				while (rs.next()){
					event = new CallendarEvent();
					event.stringToDate(rs.getString("EventDate"));
					event.setDescription(rs.getString("EventDescription"));
					event.setVenue(rs.getString("EventVenue"));
					eventList.add(event);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (ApplicationSettings.getDate() != null){
				Alert alert = AlertBoxes.returnAlert(AlertBoxType.eventsBeforeDate);
				
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.get() == ButtonType.YES){
					for (CallendarEvent e : eventList){
						LocalDate date = LocalDate.of(e.getDate().getYear(), 
								e.getDate().getMonthValue(), e.getDate().getDayOfMonth());
						if (date.isBefore(ApplicationSettings.getDate())){
							eventList.remove(e);
						}
					}
				}
			}		
		}
	}
	
	public void serializeEvents(ActionEvent e)
	{
		Alert alert = AlertBoxes.returnAlert(AlertBoxType.toXml);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.YES){

			if (eventList.size() == 0){
				alert.close();
				Alert serializeError = AlertBoxes.returnAlert(AlertBoxType.nothingToSerialize);
				
				Optional<ButtonType> result1 = serializeError.showAndWait();
			}
			
			else{
				filePath = fileChooser(fileChooserType.save);
				if (filePath != null){
					XStream xstream = new XStream();
					
					List events = new ArrayList();
					
					events.addAll(eventList);
					
					try (FileWriter fileWriter = new FileWriter(filePath)) {
						xstream.toXML(events, fileWriter);
						
					} catch (IOException e3) {
						e3.printStackTrace();
					}
				}
			}
		}
		else return;
	}
	
	public String fileChooser(fileChooserType type){
		final FileChooser fc = new FileChooser();
		final File selectedFile;
		fc.setInitialDirectory(new File("C:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("XML files (*.xml)", "*.xml"), 
										new ExtensionFilter ("ICS files (*.ics)", "*.ics"));
		
		switch(type)
		{
			case open:
			{
				fc.setTitle("Choose an XML file");				
				selectedFile = fc.showOpenDialog(null);
				if (selectedFile != null){
					return selectedFile.getAbsolutePath();
				}
					else return null;
			}
			case save:
			{
				fc.setTitle("Choose location to save a file");
				
				selectedFile = fc.showSaveDialog(null);
				
				if (selectedFile != null){
					return selectedFile.getAbsolutePath();
				}
				else return null;
			}			
		}
		return null;
	}
	
	public void deserializeEvents(ActionEvent e){
		XStream xstream = new XStream();
		List <CallendarEvent> xmlEvents;
		
		Alert alert = AlertBoxes.returnAlert(AlertBoxType.fromXml);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.NO){
			filePath = fileChooser(fileChooserType.open);
			if (filePath != null){
				try (FileReader fileReader = new FileReader(filePath)) {
	
					xmlEvents = (List <CallendarEvent>) xstream.fromXML(fileReader);
					
					eventList.addAll(xmlEvents);
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
		else{
			filePath = fileChooser(fileChooserType.open);
			if (filePath != null){
				try (FileReader fileReader = new FileReader(filePath)) {
					xmlEvents = (List <CallendarEvent>) xstream.fromXML(fileReader);
					eventList.clear();
					eventList.addAll(xmlEvents);
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	public boolean onLogOut()
	{
		Alert alert = AlertBoxes.returnAlert(AlertBoxType.logout);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES){
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
		} else if (result.get() == ButtonType.NO){
		    return false;
		}
		return true;
	}
	
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
		Alert alert = AlertBoxes.returnAlert(AlertBoxType.exit);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES)
			System.exit(0);
		else return;
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
		
		undoButton.setDisable(true);
		
		loadEvents();
		}
	}
