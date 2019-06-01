package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class Multiplication extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		if (matrices.size() < 2) { // checks at least two matrices exist.
			return Error.TWO_MATRICES;
		}
		if (matrices.get(matrices.size() - 2).getWidth() != matrices.get(matrices.size() - 1).getHeight()) {
			// checks matrices are compatible shapes.
			return Error.INCOMPATIBLE_MATRICES;
		}
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		List<Matrix> matrices = m.getMatrices();
		float[][] m1 = matrices.get(matrices.size() - 2).getMatrix();
		float[][] m2 = matrices.get(matrices.size() - 1).getMatrix();
		int m1Rows = m1.length;
        int m1Columns = m1[0].length;
        int m2Columns = m2[0].length;
        float[][] result = new float[m1Rows][m2Columns];
        
        for (int i = 0; i < m1Rows; i++) { 
            for (int j = 0; j < m2Columns; j++) { 
                for (int k = 0; k < m1Columns; k++) { 
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        
		matrices.remove(matrices.size() - 1);
		matrices.set(matrices.size() - 1, new SimpleMatrix(result));
		return null;
	}

}
