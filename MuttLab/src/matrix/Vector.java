package matrix;

public class Vector extends Matrix {

	private float[][] impl;

	public Vector(String rep) {
		impl = new float[1][];
		impl[0] = parseMatrix(rep);
	}

	private float[] parseMatrix(String rep) {
		String[] split = rep.split(", ");
		float[] result = new float[split.length];
		for (int i = 0; i < split.length; i++) {
			result[i] = Float.parseFloat(split[i]);
		}
		return result;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public int getWidth() {
		return impl[0].length;
	}

	@Override
	public float get(int row, int col) {
		return impl[0][col];
	}

	@Override
	public void set(int r, int c, float v) {
		impl[0][c] = v;
	}

	@Override
	public String toString() {
		String result = String.valueOf(impl[0][0]).replaceAll("\\.?0*$", "");
		for (int i = 1; i < impl[0].length; i++) {
			result += ", " + String.valueOf(impl[0][i]).replaceAll("\\.?0*$", "");
		}
		return result;
	}

	public Float sum() {
		float result = 0;
		for (float f : impl[0]) {
			result += f;
		}
		return result;
	}

	public Float maximal() {
		float result = impl[0][0];
		for (float f : impl[0]) {
			if (f > result)
				result = f;
		}
		return result;
	}

	public Float minimal() {
		float result = impl[0][0];
		for (float f : impl[0]) {
			if (f < result)
				result = f;
		}
		return result;
	}

	public void addFirst(Vector v) {
		for (int i = 0; i < getWidth(); i++) {
			impl[0][i] += v.get(1, i);
		}
	}

	public void addLeft(Vector v) {
		int v1 = getWidth();
		int v2 = v.getWidth();
		int max = Math.max(v1, v2);
		int min = Math.min(v1, v2);
		int abs = Math.abs(v1 - v2);
		float[][] result = new float[1][max];

		if (v2 == max) {
			for (int i = 0; i < v2; i++) {
				if (i < abs) {
					result[0][i] = v.get(1, i);
				} else
					result[0][i] = v.get(1, i) + impl[0][i - abs];
			}
			impl = result;
		} else if (v1 == max) {
			for (int i = 0; i < v1; i++) {
				if (i < abs) {
					result[0][i] = impl[0][i];
				} else
					result[0][i] = v.get(1, i - abs) + impl[0][i];
			}
			impl = result;
		}
	}

	public void addRight(Vector v) {
		int v1 = getWidth();
		int v2 = v.getWidth();
		int max = Math.max(v1, v2);
		int min = Math.min(v1, v2);
		float[][] result = new float[1][max];

		for (int i = 0; i < max; i++) {
			if (i < min) {
				result[0][i] = impl[0][i] + v.get(0, i);
			} else if (v1 == max) {
				result[0][i] = impl[0][i];
			} else if (v2 == max) {
				result[0][i] = v.get(0, i);
			}
		}
		impl = result;
	}

	public void multiply(float mult) {
		for (int i = 0; i < impl[0].length; i++) {
			impl[0][i] *= mult;
		}
	}

	@Override
	public float[][] getMatrix() {
		return impl;
	}
}
