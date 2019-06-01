package operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import matrix.Vector;
import model.Model;

public class LoadFile extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		File f = new File(command.split(" ")[1]);
		if (!f.exists()) {
			return Error.FILE_NOT_FOUND;
		}
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		try (BufferedReader br = new BufferedReader(new FileReader(command.split(" ")[1]))) {
			br.lines().map(x -> new Vector(x)).forEach(x -> m.getMatrices().add(x));
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}

}
