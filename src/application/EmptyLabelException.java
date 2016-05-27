package application;

public class EmptyLabelException extends Exception {

	public String toString(){
		return "All labels have to be filled with data!";
	}
}
