package operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.concurrent.Task;
import matrix.Matrix;
import matrix.Vector;
import model.Model;

public class LoadVector extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {

		System.out.println(command);
		File f = new File(command.split(" ")[2]);
		if (!f.exists() || f.isDirectory()) {// check that the file exists. return
			return Error.FILE_NOT_FOUND;
		}

		return null;
	}

	@Override
	public String execute(Model m, String command) {
		try (BufferedReader br = new BufferedReader(new FileReader(command.split(" ")[2]))) {
			switch (command.split(" ")[1]) {
			case "Sum":
				m.getMatrices().add(br.lines().map(x -> new Vector(x))
						.sorted(Comparator.comparing(Vector::sum).reversed()).findFirst().get());

				break;
			case "Maximal":
				m.getMatrices().add(br.lines().map(x -> new Vector(x))
						.sorted(Comparator.comparing(Vector::maximal).reversed()).findFirst().get());
				break;
			case "Minimal":
				m.getMatrices().add(br.lines().map(x -> new Vector(x))
						.sorted(Comparator.comparing(Vector::minimal).reversed()).findFirst().get());
				break;
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}
}
