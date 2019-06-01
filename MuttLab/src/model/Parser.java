package model;

import operations.*;

public class Parser {
	/*
	 * This class parses a command string and returns an operation.
	 */
	public Operation parseOperation(String command) { // checks first word against commnad enum values.
		String[] split = command.trim().split(" ");
		if (split[0].equals(Command.CREATE.toString()))
			return new CreateMatrix();
		else if (split[0].equals(Command.ADD.toString()))
			return new Addition();
		else if (split[0].equals(Command.SUBTRACT.toString()))
			return new Subtraction();
		else if (split[0].equals(Command.MULTIPLY.toString())) {
			if (split.length > 1)
				return new ScalarMultiplication();
			return new Multiplication();
		} else if (split[0].equals(Command.POINTWISE.toString()))
			return new PointwiseMultiplication();
		else if (split[0].equals(Command.SAVE.toString()))
			return new Save();
		else if (split[0].equals(Command.DUPLICATE.toString()))
			return new Duplicate();
		else
			return findCustomOp(split); // if no match then try cutom operation.
	}

	private Operation findCustomOp(String[] split) {// uses reflection to create a new instance of custom command.
		try {
			Object o = Class.forName("operations." + split[0]).newInstance();
			if (o instanceof Operation) { // checks that new command is definitely an Operation.
				return (Operation) o;
			}
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (InstantiationException e) {
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (NoClassDefFoundError e) {
			// e.printStackTrace();
		}
		return null; // if null then command not found error is returned.
	}

}
