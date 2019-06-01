package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class Subtraction extends Operation {

	@Override
	public String execute(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		float[][] current = matrices.get(matrices.size() - 1).getMatrix();
		float[][] old = matrices.get(matrices.size() - 2).getMatrix();

		for (int r = 0; r < current.length; r++) {
			for (int c = 0; c < current[r].length; c++) {
				current[r][c] -= old[r][c];
			}
		}
		matrices.remove(matrices.size() - 1);
		matrices.set(matrices.size() - 1, new SimpleMatrix(current));
		return null;
	}

	@Override
	public Error errorCheck(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		if (matrices.size() < 2) { // checks at least 2 matrices exist.
			return Error.TWO_MATRICES;
		}
		if (matrices.get(matrices.size() - 1).getHeight() != matrices.get(matrices.size() - 2).getHeight()
				|| matrices.get(matrices.size() - 1).getWidth() != matrices.get(matrices.size() - 2).getWidth()) {
			// checks matrices are same shape.
			return Error.SAME_SHAPE;
		}
		return null;
	}

}
