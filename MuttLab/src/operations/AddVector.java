package operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import matrix.Matrix;
import matrix.Vector;
import model.Model;

public class AddVector extends Operation {

	@Override
	public Error errorCheck(Model m, String command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(Model m, String command) {
		String[] params = command.split(" ");
		String option = params[1];
		String input = params[2];

		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			Vector result = new Vector(br.readLine());
			switch (option) {
			case "First":
				br.lines().map(x -> new Vector(x)).filter(x -> x.getWidth() == result.getWidth()).forEach(x -> {
					//System.out.println(x.toString());
					result.addFirst(x);
				});
				break;
			case "Left":
				br.lines().map(x -> new Vector(x)).forEach(x -> result.addLeft(x));
				break;
			case "Right":
				br.lines().map(x -> new Vector(x)).forEach(x -> result.addRight(x));
				break;
			}
			m.getMatrices().add(result);

		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
		return null;
	}

}
