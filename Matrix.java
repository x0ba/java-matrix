/**
 * A class representing a mathematical matrix and providing linear algebra operations.
 * This class supports various matrix operations including addition, multiplication,
 * row reduction, eigenvalue computation, and solving systems of linear equations.
 *
 * @author Matrix Library
 * @version 1.0
 */
public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    private static final double EPSILON = 1e-10;

    /**
     * Constructs a matrix with the specified dimensions.
     * All elements are initialized to zero.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @throws IllegalArgumentException if rows or cols are non-positive
     */
    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    /**
     * Constructs a matrix from a 2D array.
     *
     * @param data the 2D array containing matrix elements
     * @throws IllegalArgumentException if data is null, empty, or jagged
     */
    public Matrix(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Matrix data cannot be null or empty");
        }

        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    /**
     * Returns the number of rows in this matrix.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the element at the specified position.
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @return the element at position (row, col)
     * @throws IndexOutOfBoundsException if indices are out of bounds
     */
    public double get(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return data[row][col];
    }

    /**
     * Sets the element at the specified position.
     *
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @param value the value to set
     * @throws IndexOutOfBoundsException if indices are out of bounds
     */
    public void set(int row, int col, double value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        data[row][col] = value;
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other the matrix to add
     * @return a new matrix containing the sum
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return result;
    }

    /**
     * Subtracts another matrix from this matrix.
     *
     * @param other the matrix to subtract
     * @return a new matrix containing the difference
     * @throws IllegalArgumentException if matrices have different dimensions
     */
    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] - other.data[i][j];
            }
        }
        return result;
    }

    /**
     * Multiplies this matrix by another matrix.
     *
     * @param other the matrix to multiply by
     * @return a new matrix containing the product
     * @throws IllegalArgumentException if matrices have incompatible dimensions
     */
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }

        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    sum += this.data[i][k] * other.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    /**
     * Multiplies this matrix by a scalar.
     *
     * @param scalar the scalar value to multiply by
     * @return a new matrix containing the scaled result
     */
    public Matrix scale(double scalar) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] * scalar;
            }
        }
        return result;
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return a new matrix that is the transpose of this matrix
     */
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    /**
     * Computes the reduced row echelon form (RREF) of this matrix.
     * Uses Gauss-Jordan elimination.
     *
     * @return a new matrix in reduced row echelon form
     */
    public Matrix rref() {
        Matrix result = this.copy();
        int lead = 0;

        for (int r = 0; r < result.rows; r++) {
            if (lead >= result.cols) {
                break;
            }

            int i = r;
            while (Math.abs(result.data[i][lead]) < EPSILON) {
                i++;
                if (i == result.rows) {
                    i = r;
                    lead++;
                    if (lead == result.cols) {
                        return result;
                    }
                }
            }

            // Swap rows i and r
            double[] temp = result.data[i];
            result.data[i] = result.data[r];
            result.data[r] = temp;

            // Scale row r to make pivot 1
            double pivot = result.data[r][lead];
            if (Math.abs(pivot) > EPSILON) {
                for (int j = 0; j < result.cols; j++) {
                    result.data[r][j] /= pivot;
                }
            }

            // Eliminate all other entries in column
            for (int i2 = 0; i2 < result.rows; i2++) {
                if (i2 != r) {
                    double factor = result.data[i2][lead];
                    for (int j = 0; j < result.cols; j++) {
                        result.data[i2][j] -= factor * result.data[r][j];
                    }
                }
            }

            lead++;
        }

        // Clean up near-zero values
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                if (Math.abs(result.data[i][j]) < EPSILON) {
                    result.data[i][j] = 0;
                }
            }
        }

        return result;
    }

    /**
     * Computes the determinant of this matrix using LU decomposition.
     *
     * @return the determinant value
     * @throws IllegalArgumentException if the matrix is not square
     */
    public double determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Determinant requires a square matrix");
        }

        Matrix lu = this.copy();
        double det = 1.0;

        for (int i = 0; i < rows; i++) {
            // Find pivot
            int maxRow = i;
            for (int k = i + 1; k < rows; k++) {
                if (Math.abs(lu.data[k][i]) > Math.abs(lu.data[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Swap rows if needed
            if (maxRow != i) {
                double[] temp = lu.data[i];
                lu.data[i] = lu.data[maxRow];
                lu.data[maxRow] = temp;
                det *= -1;
            }

            if (Math.abs(lu.data[i][i]) < EPSILON) {
                return 0.0;
            }

            det *= lu.data[i][i];

            // Eliminate below
            for (int k = i + 1; k < rows; k++) {
                double factor = lu.data[k][i] / lu.data[i][i];
                for (int j = i; j < cols; j++) {
                    lu.data[k][j] -= factor * lu.data[i][j];
                }
            }
        }

        return det;
    }

    /**
     * Computes the trace of this matrix (sum of diagonal elements).
     *
     * @return the trace of the matrix
     * @throws IllegalArgumentException if the matrix is not square
     */
    public double trace() {
        if (rows != cols) {
            throw new IllegalArgumentException("Trace requires a square matrix");
        }

        double sum = 0;
        for (int i = 0; i < rows; i++) {
            sum += data[i][i];
        }
        return sum;
    }

    /**
     * Computes the eigenvalues of this matrix using the QR algorithm.
     * This is an iterative numerical method that may not converge for all matrices.
     *
     * @return an array containing the eigenvalues
     * @throws IllegalArgumentException if the matrix is not square
     */
    public double[] eigenvalues() {
        if (rows != cols) {
            throw new IllegalArgumentException("Eigenvalues require a square matrix");
        }

        Matrix A = this.copy();
        int maxIterations = 1000;

        // QR algorithm
        for (int iter = 0; iter < maxIterations; iter++) {
            QRDecomposition qr = A.qrDecomposition();
            A = qr.R.multiply(qr.Q);

            // Check for convergence (off-diagonal elements near zero)
            boolean converged = true;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i != j && Math.abs(A.data[i][j]) > EPSILON) {
                        converged = false;
                        break;
                    }
                }
                if (!converged) break;
            }

            if (converged) break;
        }

        // Extract eigenvalues from diagonal
        double[] eigenvalues = new double[rows];
        for (int i = 0; i < rows; i++) {
            eigenvalues[i] = A.data[i][i];
        }

        return eigenvalues;
    }

    /**
     * Solves the matrix equation Ax = b for x.
     * Uses Gaussian elimination with back substitution.
     *
     * @param b the right-hand side vector (as a column matrix)
     * @return the solution vector x (as a column matrix)
     * @throws IllegalArgumentException if dimensions are incompatible or system is singular
     */
    public Matrix solve(Matrix b) {
        if (this.rows != this.cols) {
            throw new IllegalArgumentException("Coefficient matrix must be square");
        }
        if (b.rows != this.rows || b.cols != 1) {
            throw new IllegalArgumentException("Right-hand side must be a column vector of compatible size");
        }

        // Create augmented matrix [A|b]
        Matrix augmented = new Matrix(rows, cols + 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented.data[i][j] = this.data[i][j];
            }
            augmented.data[i][cols] = b.data[i][0];
        }

        // Forward elimination
        for (int i = 0; i < rows; i++) {
            // Find pivot
            int maxRow = i;
            for (int k = i + 1; k < rows; k++) {
                if (Math.abs(augmented.data[k][i]) > Math.abs(augmented.data[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Swap rows
            double[] temp = augmented.data[i];
            augmented.data[i] = augmented.data[maxRow];
            augmented.data[maxRow] = temp;

            if (Math.abs(augmented.data[i][i]) < EPSILON) {
                throw new IllegalArgumentException("Matrix is singular or system has no unique solution");
            }

            // Eliminate below
            for (int k = i + 1; k < rows; k++) {
                double factor = augmented.data[k][i] / augmented.data[i][i];
                for (int j = i; j <= cols; j++) {
                    augmented.data[k][j] -= factor * augmented.data[i][j];
                }
            }
        }

        // Back substitution
        Matrix x = new Matrix(rows, 1);
        for (int i = rows - 1; i >= 0; i--) {
            double sum = augmented.data[i][cols];
            for (int j = i + 1; j < cols; j++) {
                sum -= augmented.data[i][j] * x.data[j][0];
            }
            x.data[i][0] = sum / augmented.data[i][i];
        }

        return x;
    }

    /**
     * Creates an identity matrix of the specified size.
     *
     * @param size the size of the identity matrix
     * @return an identity matrix of size n×n
     */
    public static Matrix identity(int size) {
        Matrix result = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            result.data[i][i] = 1.0;
        }
        return result;
    }

    /**
     * Creates a deep copy of this matrix.
     *
     * @return a new matrix with the same elements
     */
    public Matrix copy() {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(this.data[i], 0, result.data[i], 0, cols);
        }
        return result;
    }

    /**
     * Performs QR decomposition of this matrix using Gram-Schmidt orthogonalization.
     *
     * @return a QRDecomposition object containing matrices Q and R
     */
    private QRDecomposition qrDecomposition() {
        Matrix Q = new Matrix(rows, cols);
        Matrix R = new Matrix(cols, cols);

        for (int j = 0; j < cols; j++) {
            // Copy column j
            double[] v = new double[rows];
            for (int i = 0; i < rows; i++) {
                v[i] = data[i][j];
            }

            // Orthogonalize against previous columns
            for (int k = 0; k < j; k++) {
                double dot = 0;
                for (int i = 0; i < rows; i++) {
                    dot += Q.data[i][k] * data[i][j];
                }
                R.data[k][j] = dot;
                for (int i = 0; i < rows; i++) {
                    v[i] -= dot * Q.data[i][k];
                }
            }

            // Normalize
            double norm = 0;
            for (int i = 0; i < rows; i++) {
                norm += v[i] * v[i];
            }
            norm = Math.sqrt(norm);
            R.data[j][j] = norm;

            if (norm > EPSILON) {
                for (int i = 0; i < rows; i++) {
                    Q.data[i][j] = v[i] / norm;
                }
            }
        }

        return new QRDecomposition(Q, R);
    }

    /**
     * A helper class to store QR decomposition results.
     */
    private static class QRDecomposition {
        Matrix Q;
        Matrix R;

        QRDecomposition(Matrix Q, Matrix R) {
            this.Q = Q;
            this.R = R;
        }
    }

    /**
     * Returns a string representation of this matrix.
     *
     * @return a formatted string showing the matrix elements
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%10.4f", data[i][j]));
                if (j < cols - 1) {
                    sb.append(" ");
                }
            }
            sb.append("]");
            if (i < rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}