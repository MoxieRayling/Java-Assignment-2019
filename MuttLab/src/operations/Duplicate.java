package operations;

import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class Duplicate extends Operation {

	@Override
	public Error errorCheck(Model m, String command) { // checks at least one matrix exists.
		if (m.getMatrices().size() < 1) {
			return Error.EMPTY_LIST;
		}
		return null;
	}

	@Override
	public String execute(Model m, String command) { // duplicates last matrix in list.
		List<Matrix> matrices = m.getMatrices();
		matrices.add(new SimpleMatrix(matrices.get(matrices.size() - 1).getMatrix()));
		return null;
	}

}
