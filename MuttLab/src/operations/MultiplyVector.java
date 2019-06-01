package operations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import matrix.Matrix;
import matrix.Vector;
import model.Model;

public class MultiplyVector extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		System.out.println("me");
		String[] params = command.split(" ");
		float mult = Float.parseFloat(params[1]);
		String output = params[params.length - 1];
		List<Vector> vectors = new ArrayList<Vector>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("matrices.csv"));
			vectors = br.lines().map(x -> new Vector(x)).collect(Collectors.toList());
			//System.out.println(vectors.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fileWriter = new FileWriter(output);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Vector v : vectors) {
				v.multiply(mult);
				bufferedWriter.write(v.toString() + "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}

		return null;
	}

}
