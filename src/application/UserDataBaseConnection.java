package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.sqlite.SQLiteConfig;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class UserDataBaseConnection {

	Statement stmt = null;
	Connection connection = null;
	String dbURL = "jdbc:sqlite:product.db";
    
	public Connection connectLogin(String path) throws FileNotFoundException{
	try ( Connection conn = DriverManager.getConnection(dbURL);) {
		File f = new File(path);
		if (f.exists()){
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.setReadOnly(true);
			connection=DriverManager.getConnection("jdbc:sqlite:" + path, config.toProperties());
			System.out.println("Polaczono z baza danych");
			stmt = connection.createStatement();
		    return connection;
		}
		else throw new Exception();
    } catch ( Exception e ) {
      throw new FileNotFoundException();
    }
}
	
	public Connection connectRegister(String path) throws FileNotFoundException{
		try ( Connection conn = DriverManager.getConnection(dbURL);) {
			File f = new File(path);
			if (f.exists()){
				Class.forName("org.sqlite.JDBC");
				SQLiteConfig config = new SQLiteConfig();
				config.setReadOnly(false);
				connection=DriverManager.getConnection("jdbc:sqlite:" + path, config.toProperties());
				System.out.println("Polaczono z baza danych");
				stmt = connection.createStatement();
			    return connection;
			}
			else throw new Exception();
	    } catch ( Exception e ) {
	      throw new FileNotFoundException();
	    }
	}
}

