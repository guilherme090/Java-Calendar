package db;

/**
 * 
 * @author Guilherme Virgilio P. O. Simoes 
 * @version 1.1.0
 * 
 * Transformation of CS-102 project into a Maven project
 */
public enum MessageBoardOptions {
	MESSAGE(0), RESULTS(1);
    // MESSAGE: reference to the GUI's message board
    // RESULTS: reference to the GUI's results board

	private final int option;
	
	MessageBoardOptions(int optionNumber){
		option = optionNumber;
	}
	
	public int getOption() {
		return option;
	}

}