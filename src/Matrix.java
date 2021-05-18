
// Primarily used as a 3x3 matrix
public class Matrix {
	double[] values; // Consider using 2D array (or maybe this is more efficient)
	
	public Matrix(double[] vals) {
		this.values = vals;
	}
	
	
	Matrix multiply(Matrix matrix2) { // Multiplication with another 3x3
		double[] new_matrix_values = new double[9];		
		
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    new_matrix_values[row * 3 + col] += this.values[row * 3 + i] * matrix2.values[i * 3 + col];
                }
            }
        }
		return new Matrix(new_matrix_values);
	}
	
	Point transform(Point pointToTransform) {
		
		// Pretty much multiplication but on a 3x1.
        return new Point(
        		pointToTransform.x * values[0] + pointToTransform.y * values[3] + pointToTransform.z * values[6],
                pointToTransform.x * values[1] + pointToTransform.y * values[4] + pointToTransform.z * values[7],
                pointToTransform.x * values[2] + pointToTransform.y * values[5] + pointToTransform.z * values[8]
            );
	}
	
}
