package operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import matrix.Matrix;
import matrix.SimpleMatrix;
import model.Model;

public class Save extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {

		File file = new File(command.split(" ")[1]);
		if (!file.canWrite()) // checks file is writable.
			return Error.WRITE_FAILURE;
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		String output = command.split(" ")[1];
		try {
			FileWriter fileWriter = new FileWriter(output);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Matrix matrix : m.getMatrices()) {
				bufferedWriter.write(matrix.toString() + "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}

}
