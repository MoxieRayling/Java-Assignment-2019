package matrix;

import java.util.Arrays;

/**
 * A simple SimpleMatrix class
 * 
 * @author rej
 */
public class SimpleMatrix extends Matrix {

	private final float[][] impl;

	/**
	 * Create a matrix
	 * 
	 * @param height
	 *            of the matrix
	 * @param width
	 *            of the matrix
	 */
	public SimpleMatrix(int height, int width) {
		impl = new float[height][width];
	}

	/**
	 * Create a matrix
	 * 
	 * @param rep
	 *            flattened representation of the matrix
	 */
	public SimpleMatrix(String rep) {
		impl = parseMatrix(rep);
	}

	public SimpleMatrix(float[][] matrix) {
		impl = matrix;
	}

	/**
	 * Deep copy of a Matrix
	 * 
	 * @param old
	 *            the matrix to copy
	 */
	public SimpleMatrix(Matrix old) {
		int height = old.getHeight();
		int width = old.getWidth();
		impl = new float[height][width];
		if (old instanceof Vector) {
			for (int c = 0; c < width; c++)
				impl[0][c] = old.get(0, c);
		} else {
			for (int r = 0; r < height; r++)
				for (int c = 0; c < width; c++)
					impl[r][c] = old.get(r, c);
		}
	}

	/**
	 * Get a row of a matrix
	 * 
	 * @param row
	 * @return the row
	 */
	private float[] getRow(int row) {
		return impl[row];
	}

	/**
	 * Parse a flattened representation of a matrix
	 * 
	 * @param rep
	 *            the flattened representation
	 * @return
	 */
	private float[][] parseMatrix(String rep) {
		// Remove any trailing ]
		int close = rep.indexOf(']');
		if (close != -1)
			rep = rep.substring(0, close);
		String[] rows = rep.split(";");

		float[][] mat = new float[rows.length][];
		for (int r = 0; r < rows.length; r++) {
			String[] elements = rows[r].trim().split(" ");
			mat[r] = new float[elements.length];
			for (int c = 0; c < elements.length; c++) {
				mat[r][c] = Float.parseFloat(elements[c].trim());
			}
		}
		return mat;
	}

	@Override
	public int getHeight() {
		return impl.length;
	}

	@Override
	public int getWidth() {
		return (impl.length == 0 || impl[0] == null) ? 0 : impl[0].length;
	}

	@Override
	public float get(int r, int c) {
		return impl[r][c];
	}

	@Override
	public void set(int r, int c, float v) {
		impl[r][c] = v;
	}

	@Override
	public float[][] getMatrix() {
		return impl.clone();
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < impl.length; i++) {
			result += String.valueOf(impl[i][0]).replaceAll("\\.?0*$", "");
			for (int j = 1; j < impl[0].length; j++) {
				result += ", " + String.valueOf(impl[i][j]).replaceAll("\\.?0*$", "");
			}
			if (i < impl.length - 1)
				result += "; ";
		}
		return result;
	}
}
