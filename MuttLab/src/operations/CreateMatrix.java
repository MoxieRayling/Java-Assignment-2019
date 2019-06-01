package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class CreateMatrix extends Operation {
	/*
	 * Creates matrix from command string.
	 */

	@Override
	public String execute(Model m, String command) {
		int close = command.indexOf(']');
		if (close != -1)
			command = command.substring(0, close); // removes ] if exists.
		int start = command.indexOf('[');
		if (start != -1)
			command = command.substring(start + 1, command.length()); // rmeoves [
		String[] rows = command.split(";");	// splits rows.

		float[][] mat = new float[rows.length][];
		for (int r = 0; r < rows.length; r++) {
			String[] elements = rows[r].trim().split(" "); //splits columns.
			mat[r] = new float[elements.length];
			for (int c = 0; c < elements.length; c++) {
				mat[r][c] = Float.parseFloat(elements[c].trim()); //parses floats from strings.
			}
		}
		m.getMatrices().add(new SimpleMatrix(mat));
		return null;
	}

	@Override
	public Error errorCheck(Model m, String command) { 
		int close = command.indexOf(']');
		if (close != -1)
			command = command.substring(0, close);
		int start = command.indexOf('[');
		if (start != -1)
			command = command.substring(start + 1, command.length());
		String[] rows = command.split(";");
		int columns = 0;
		try {
			for (int r = 0; r < rows.length; r++) {
				String[] elements = rows[r].trim().split(" ");
				if (columns > 0 && columns != elements.length || elements.length == 0) // checks matrix is rectangular.
					return Error.NON_RECTANGULAR;
				columns = elements.length;
				for (int c = 0; c < elements.length; c++) {
					Float.parseFloat(elements[c].trim());
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return Error.NON_RECTANGULAR;
		} catch (NumberFormatException e) {
			return Error.NUMBER_NOT_PARSED; // catches non-number values.
		}

		return null;
	}

}
