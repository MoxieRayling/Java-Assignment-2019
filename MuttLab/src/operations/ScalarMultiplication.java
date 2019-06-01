package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class ScalarMultiplication extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		String[] params = command.split(" ");
		try {
			Float.parseFloat(params[1]); // checks operand exists.
		} catch (NumberFormatException e) {
			return Error.SCALAR_MULTIPLY;
		} catch (IndexOutOfBoundsException e) {
			return Error.SCALAR_MULTIPLY;
		}
		if (m.getMatrices().size() < 1) { // checks matrix exists.
			return Error.EMPTY_LIST;
		}
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		float scalar = Float.parseFloat(command.split(" ")[1]);
		float[][] matrix = matrices.get(matrices.size() - 1).getMatrix();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] *= scalar;
			}
		}
		matrices.set(matrices.size() - 1, new SimpleMatrix(matrix));
		System.out.println("its meeee");
		return null;
	}
}
