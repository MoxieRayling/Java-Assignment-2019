package operations;

public enum Command {
	// enum to store command words.
	CREATE("["),
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	POINTWISE(".*"),
	SAVE("save"),
	DUPLICATE("dup"),
	HELP("help"),
	SCRIPT("script"),
	QUIT("quit");
	
	private String command;
	
    Command(String command)
    {
        this.command = command;
    }

    public String toString()
    {
        return command;
    }

}