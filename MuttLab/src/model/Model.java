package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import matrix.Matrix;
import operations.*;
import operations.Error;

public class Model {
	/*
	 * This class represents the basic version of the program. Its job is to receive
	 * input from the controller, get an Operation from the parser, and then execute
	 * said operation. There are exceptions for Script, Quit and Help as these are
	 * system commands that do not operate on the data structure.
	 */
	private Controller controller;
	private Parser parser;
	private ArrayList<Matrix> matrices;

	public Model() {
		matrices = new ArrayList<Matrix>();
		parser = new Parser();
	}

	public void setController(Controller c) {
		this.controller = c;
	}

	public ArrayList<Matrix> command(String command) { // checks command for exceptions Script Help or Quit.
		String[] split = command.split(" ");
		if (split.length < 1) {
			controller.printError(Error.COMMAND_NOT_FOUND);
			return matrices;
		}
		split[0].toLowerCase();
		if (split[0].equals(Command.QUIT.toString())) {
			controller.quit();
		} else if (split[0].equals(Command.SCRIPT.toString())) {
			script(command);
			return matrices;
		} else if (split[0].equals(Command.HELP.toString())) {
			controller.help();
		} else if (executeOp(command)) { // if no exceptions then execute command.
			controller.printMatrices(matrices.stream().map(m -> m.toString()).collect(Collectors.toList()));
		}
		return matrices;
	}

	private boolean script(String command) {
		String[] split = command.split(" ");
		if (split.length < 2) { // check file is in parameters.
			controller.printError(Error.FILE_NOT_FOUND);
			return false;
		}
		File f = new File(split[1]);
		if (!f.exists() || f.isDirectory()) {// check that the file exists.
			controller.printError(Error.FILE_NOT_FOUND);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(split[1]))) {// read in file.
			String line;
			while ((line = br.readLine()) != null) {
				controller.printMessage(line); // print line in script.
				if (line.startsWith(Command.QUIT.toString())) { // catch for quit in script
					return true;
				}
				command(line);

			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return false;
	}

	private boolean executeOp(String command) {
		Operation op = parser.parseOperation(command); // parser parse command string, returns an operation.
		if (op == null) { // if op is null: print error.
			controller.printError(Error.COMMAND_NOT_FOUND);
			return false;
		}
		Error error = op.errorCheck(this, command);// error check
		if (error != null) {
			controller.printError(error);
			return false; // if error then return, else execute.
		}
		String result = op.execute(this, command); // execute
		if (result != null) { // if operation returns a string: print message
			controller.printMessage(result);
		}
		return true;
	}

	public ArrayList<Matrix> getMatrices() {
		return this.matrices;
	}

	public Matrix getLastMatrix() {
		return matrices.get(matrices.size() - 1);
	}
}
