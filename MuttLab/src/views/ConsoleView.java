package views;

import java.util.List;
import java.util.Scanner;

import controller.Controller;
import operations.Error;

import operations.Command;

public class ConsoleView implements View {
	/*
	 * This class manages the interface with the console, takes input and passes it
	 * to the controller. Output is received from the controller and
	 * printed/formatted here.
	 */

	private Scanner reader;
	private boolean running;
	private Controller c;

	public ConsoleView() {
		super();
		reader = new Scanner(System.in);
	}

	public void exec() { // main loop, passes input until the running field is set to false.
		running = true;
		System.out.println("Welcome to MuttLab, type help for instructions.");
		while (running) {
			c.command(reader.nextLine());
		}
	}

	@Override
	public void printError(Error e) {
		System.out.println(e.toString());
	}

	@Override
	public void printMessage(String string) {
		System.out.println(string);
	}

	@Override
	public void help() {
		System.out.println("The commnad words are: " + Command.CREATE.toString() + " " + Command.ADD.toString() + " "
				+ Command.SUBTRACT.toString() + " " + Command.MULTIPLY.toString() + " " + Command.SAVE.toString() + " "
				+ Command.DUPLICATE.toString() + " " + Command.HELP.toString() + " " + Command.SCRIPT.toString() + " "
				+ Command.QUIT.toString() + ".");
	}

	@Override
	public void printMatrices(List<String> matrices) {
		System.out.println("Stored Matrices:");
		for (String s : matrices) {
			System.out.println(s);
		}
	}

	@Override
	public void quit() { // sets running to false, ends the main loop.
		running = false;
		System.out.println("Goodbye.");
	}

	@Override
	public void setController(Controller c) {
		this.c = c;
	}


}
