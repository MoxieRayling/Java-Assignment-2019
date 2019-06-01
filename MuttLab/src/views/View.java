package views;

import java.util.List;

import controller.Controller;
import operations.Error;

public abstract interface View {

	/*
	 * blueprint for view. views must implement the methods below but may do so as
	 * console or GUI.
	 */

	public void setController(Controller c);

	public void printError(Error e);

	public void printMessage(String string);

	public void help();

	public void printMatrices(List<String> formattedMatrices);

	public void quit();
}
