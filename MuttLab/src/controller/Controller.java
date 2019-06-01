package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import matrix.Matrix;
import model.Model;
import views.View;
import operations.Error;

public class Controller {
	
	/*
	 * Controller class restricts access between view and model classes.
	 */

	private Model model;
	private View view;

	public Controller(Model m, View v) {
		this.model = m;
		this.view = v;
	}

	public ArrayList<Matrix> command(String command) {
		return model.command(command);
	}

	public void printError(Error e) {
		view.printError(e);
	}

	public void printMessage(String string) {
		view.printMessage(string);
	}

	public void help() {
		view.help();
	}

	public void printMatrices(List<String> formattedMatrices) {
		view.printMatrices(formattedMatrices);
	}

	public void quit() {
		view.quit();
	}

	public Matrix getLastMatrix() {
		return model.getLastMatrix();
	}

	public ArrayList<Matrix> getMatrices() {
		return model.getMatrices();
	}
}
