package operations;

public enum Error {
	/*
	 * This enum contains error messages. Add or modify messages here.
	 */
	COMMAND_NOT_FOUND("Command not found, type \"help\" for a list of commands."),
	FILE_NOT_FOUND("File could not be found."),
	ERROR_ON_LINE("Error on line "),
	TWO_MATRICES("Two matrices required to add."),
	SAME_SHAPE("Matrices are not the same shape."),
	EMPTY_LIST("Requires at least one matrix."),
	INCOMPATIBLE_MATRICES("Matrices must be compatible shapes."),
	WRITE_FAILURE("Unable to write file."),
	SCALAR_MULTIPLY("Could not parse value to multiply by."),
	NON_RECTANGULAR("Matrix is not rectangular."),
	NUMBER_NOT_PARSED("Could not parse number in matrix.");
	
	private String errorMessage;
	
    Error(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String toString()
    {
        return errorMessage;
    }

}
