package operations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import matrix.Vector;
import model.Model;

public class VectorLength extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		String[] params = command.split(" ");
		int length = Integer.parseInt(params[1]);
		String output = params[2];
		List<Vector> vectors = new ArrayList<Vector>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("matrices.csv"));
			vectors = br.lines().map(x -> new Vector(x)).filter(x -> x.getWidth() == length)
					.collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fileWriter = new FileWriter(output);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Vector v : vectors) {
				bufferedWriter.write(v.toString() + "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}

}
