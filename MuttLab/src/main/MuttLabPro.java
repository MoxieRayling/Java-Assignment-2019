package main;

import controller.Controller;
import model.Model;
import model.ProModel;
import views.ConsoleView;
import views.View;

public class MuttLabPro {
	public static void main(String[] args) {
		Model m = new ProModel(); // creates pro version of the program.
		View v = new ConsoleView();
		Controller c = new Controller(m, v);
		m.setController(c);
		v.setController(c);
		((ConsoleView) v).exec();
	}
}
