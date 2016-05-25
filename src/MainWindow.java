import javafx.fxml.FXMLLoader;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainWindow extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root, 500, 300);
		primaryStage.setTitle("Terminarz Java");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// TODO Auto-generated method stub
		
	}

}
