package xyz.marsavic.gfxlab;


import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.gui.UtilsGL;

import java.util.Arrays;


public final class MatrixDoubles implements Matrix<Double> {

	private final int width, height;
	private final double[] data;   // TODO test an implementation with int[][] and compare performances.


	public MatrixDoubles(Vector size) {
		width = size.xInt();
		height = size.yInt();
		data = new double[width * height];
	}
	
	
	public int height() {
		return height;
	}
	
	
	public int width() {
		return width;
	}
	
	
	@Override
	public Vector size() {
		return Vector.xy(width(), height());
	}
	
	
	@Override
	public Double get(int x, int y) {
		return data[y * width + x];
	}
	
	
	@Override
	public void set(int x, int y, Double value) {
		data[y * width + x] = value;
	}
	
	
	public void copyFrom(Matrix<Double> source) {
		Matrix.assertEqualSizes(this, source);
		
		if (source instanceof MatrixDoubles m) {
			System.arraycopy(m.array(), 0, data, 0, m.array().length);
/*
			// Optimize: Test if doing it in parallel is faster.
			UtilsGL.parallel(height, y -> {
				int o = y * width;
				System.arraycopy(m.array(), o, data, o, width);
			});
*/
		} else {
			UtilsGL.parallel(height, y -> {
				int o = y * width;
				for (int x = 0; x < width; x++) {
					data[o++] = source.get(x, y);
				}
			});
		}
	}
	
	
	@Override
	public void fill(Double value) {
		Arrays.fill(data, value);   // Optimize: Parallelism on blocks might be faster?
	}
	
	
	public double[] array() {
		return data;
	}
	
}
