public class Main {

    public static void main(String[] args) {
        System.out.println("=== Matrix Operations Examples ===\n");

        // Example 1: Matrix creation and basic operations
        example1_BasicOperations();

        // Example 2: Matrix multiplication
        example2_MatrixMultiplication();

        // Example 3: Transpose
        example3_Transpose();

        // Example 4: Determinant
        example4_Determinant();

        // Example 5: Trace
        example5_Trace();

        // Example 6: Reduced Row Echelon Form (RREF)
        example6_RREF();

        // Example 7: Solving systems of linear equations
        example7_SolveLinearSystem();

        // Example 8: Eigenvalues
        example8_Eigenvalues();

        // Example 9: Identity matrix
        example9_IdentityMatrix();
    }

    private static void example1_BasicOperations() {
        System.out.println("Example 1: Basic Operations (Addition, Subtraction, Scaling)");
        System.out.println("-----------------------------------------------------------");

        double[][] data1 = {{1, 2, 3}, {4, 5, 6}};
        double[][] data2 = {{7, 8, 9}, {10, 11, 12}};

        Matrix A = new Matrix(data1);
        Matrix B = new Matrix(data2);

        System.out.println("Matrix A:");
        System.out.println(A);
        System.out.println("\nMatrix B:");
        System.out.println(B);

        System.out.println("\nA + B:");
        System.out.println(A.add(B));

        System.out.println("\nA - B:");
        System.out.println(A.subtract(B));

        System.out.println("\n2 * A:");
        System.out.println(A.scale(2));

        System.out.println("\n");
    }

    private static void example2_MatrixMultiplication() {
        System.out.println("Example 2: Matrix Multiplication");
        System.out.println("--------------------------------");

        double[][] data1 = {{1, 2, 3}, {4, 5, 6}};
        double[][] data2 = {{7, 8}, {9, 10}, {11, 12}};

        Matrix A = new Matrix(data1);  // 2x3
        Matrix B = new Matrix(data2);  // 3x2

        System.out.println("Matrix A (2x3):");
        System.out.println(A);
        System.out.println("\nMatrix B (3x2):");
        System.out.println(B);

        System.out.println("\nA * B:");
        System.out.println(A.multiply(B));

        System.out.println("\n");
    }

    private static void example3_Transpose() {
        System.out.println("Example 3: Transpose");
        System.out.println("--------------------");

        double[][] data = {{1, 2, 3}, {4, 5, 6}};
        Matrix A = new Matrix(data);

        System.out.println("Matrix A:");
        System.out.println(A);

        System.out.println("\nTranspose of A:");
        System.out.println(A.transpose());

        System.out.println("\n");
    }

    private static void example4_Determinant() {
        System.out.println("Example 4: Determinant");
        System.out.println("----------------------");

        double[][] data = {{4, 3, 2}, {3, 2, 1}, {1, 2, 3}};
        Matrix A = new Matrix(data);

        System.out.println("Matrix A:");
        System.out.println(A);

        System.out.printf("\nDeterminant of A: %.4f\n", A.determinant());

        System.out.println("\n");
    }

    private static void example5_Trace() {
        System.out.println("Example 5: Trace");
        System.out.println("----------------");

        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix A = new Matrix(data);

        System.out.println("Matrix A:");
        System.out.println(A);

        System.out.printf("\nTrace of A (sum of diagonal): %.4f\n", A.trace());

        System.out.println("\n");
    }

    private static void example6_RREF() {
        System.out.println("Example 6: Reduced Row Echelon Form (RREF)");
        System.out.println("-------------------------------------------");

        double[][] data = {{2, 1, -1, 8}, {-3, -1, 2, -11}, {-2, 1, 2, -3}};
        Matrix A = new Matrix(data);

        System.out.println("Matrix A:");
        System.out.println(A);

        System.out.println("\nRREF of A:");
        System.out.println(A.rref());

        System.out.println("\n");
    }

    private static void example7_SolveLinearSystem() {
        System.out.println("Example 7: Solving Linear Systems (Ax = b)");
        System.out.println("------------------------------------------");

        // System: 2x + y - z = 8
        //        -3x - y + 2z = -11
        //        -2x + y + 2z = -3

        double[][] coeffData = {{2, 1, -1}, {-3, -1, 2}, {-2, 1, 2}};
        double[][] rhsData = {{8}, {-11}, {-3}};

        Matrix A = new Matrix(coeffData);
        Matrix b = new Matrix(rhsData);

        System.out.println("Coefficient Matrix A:");
        System.out.println(A);
        System.out.println("\nRight-hand side b:");
        System.out.println(b);

        Matrix x = A.solve(b);
        System.out.println("\nSolution x:");
        System.out.println(x);

        // Verify: A * x should equal b
        System.out.println("\nVerification (A * x):");
        System.out.println(A.multiply(x));

        System.out.println("\n");
    }

    private static void example8_Eigenvalues() {
        System.out.println("Example 8: Eigenvalues");
        System.out.println("----------------------");

        double[][] data = {{4, -2}, {1, 1}};
        Matrix A = new Matrix(data);

        System.out.println("Matrix A:");
        System.out.println(A);

        double[] eigenvalues = A.eigenvalues();
        System.out.println("\nEigenvalues:");
        for (int i = 0; i < eigenvalues.length; i++) {
            System.out.printf("λ%d = %.4f\n", i + 1, eigenvalues[i]);
        }

        System.out.println("\n");
    }

    private static void example9_IdentityMatrix() {
        System.out.println("Example 9: Identity Matrix");
        System.out.println("--------------------------");

        Matrix I = Matrix.identity(3);
        System.out.println("3x3 Identity Matrix:");
        System.out.println(I);

        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix A = new Matrix(data);

        System.out.println("\nMatrix A:");
        System.out.println(A);

        System.out.println("\nA * I (should equal A):");
        System.out.println(A.multiply(I));

        System.out.println("\nI * A (should equal A):");
        System.out.println(I.multiply(A));

        System.out.println("\n");
    }
}
