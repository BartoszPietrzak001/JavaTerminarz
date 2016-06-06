package application;

public class NullDateException extends Exception {

	@Override
	public String toString()
	{
		return "You've not set a full arrangement date (yyyy-mm-dd)";
	}

}
