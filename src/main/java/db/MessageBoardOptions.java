package db;

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