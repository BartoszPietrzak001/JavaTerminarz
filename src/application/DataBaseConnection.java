package application;

import java.sql.Connection;
import java.sql.Statement;

public class DataBaseConnection {

	Connection connection = null;
	Statement stmt = null;
//	try{
//		Class.forName("org.sqlite.JDBC");
//		connection=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Bartek\\workspace\\DataBase.sqlite");
//		System.out.println("Polaczono z baza danych");
//	  stmt = connection.createStatement();
//	  System.out.println("CREATE TABLE  " + "Obiekt" + " (" + query + ")");
//      String sql = "CREATE TABLE  " + "Obiekt" +  " (" + query + ")";
//      stmt.executeUpdate(sql);
//      stmt.close();
//      connection.close();
//    } catch ( Exception e ) {
//      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      System.exit(0);
//    }
//    System.out.println("Table created successfully");
//	return null;
}
