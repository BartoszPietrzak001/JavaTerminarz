package application;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertBoxes extends Alert {

	public AlertBoxes(AlertType alertType) {
		super(alertType);
		// TODO Auto-generated constructor stub
	}

	public static enum AlertBoxType{
		exit, toXml, fromXml, logout, toIcs, fromIcs, save, nothingToSerialize, saveChanges, eventsBeforeDate
	}
	
	public static Alert returnAlert(AlertBoxType type){
		String contentText;
		
		switch(type)
		{
			case exit:
			{
				contentText = new String("Are you sure you want to exit?");
				Alert alert = new Alert(Alert.AlertType.WARNING, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case toXml:
			{
				contentText = new String("Are you sure you want to serialize those events to .xml format?");
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case toIcs:
			{
				contentText = new String("Are you sure you want to serialize those events to .ics format?");
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case fromXml:
			case fromIcs:
			{
				contentText = new String("Erease event list before importing?");
				
				Alert alert = new Alert(Alert.AlertType.WARNING, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case save:
			{
				contentText = new String("Data base updated");
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION, contentText, ButtonType.OK);
				
				return alert;
			}
			case logout:
			{
				contentText = new String("Are you sure you want to log out?");
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case nothingToSerialize:
			{
				contentText = new String("I have nothing to serialize!");
				
				Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.OK);
				
				return alert;
			}
			case saveChanges:
			{
				contentText = new String("Save changes?");
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			case eventsBeforeDate:
			{
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//				String date = new String(ApplicationSettings.getDate().format(formatter));
				contentText = new String("Delete events before date: "+ "?");
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.YES, ButtonType.NO);
				
				return alert;
			}
			default: return null;
		}
	}
}
