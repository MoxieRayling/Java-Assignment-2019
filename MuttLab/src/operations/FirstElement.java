package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class FirstElement extends Operation {

	/*
	 * test operation, returns the first element in the matrix. This is used to
	 * check custom command methods.
	 */

	@Override
	public Error errorCheck(Model m, String command) {
		if (m.getMatrices().size() < 1) {
			return Error.EMPTY_LIST;
		}
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		return String.valueOf(matrices.get(matrices.size() - 1).getMatrix()[0][0]).replaceAll("\\.?0*$", "");
	}

}
