package operations;

import java.util.List;

import matrix.Matrix;
import model.Model;

public abstract class Operation {
	/*
	 * Blueprint for Operation. Sub classes must override the methods below.
	 */

	public abstract Error errorCheck(Model m, String command);
	
	public abstract String execute(Model m, String command);

}
