import java.util.*;

/**
 * An interactive CLI calculator for matrix operations.
 * Designed for linear algebra students with minimal programming experience.
 */
public class MatrixCalculator {
    private Map<String, Matrix> matrices;
    private Scanner scanner;
    private static final String VERSION = "1.0";

    public MatrixCalculator() {
        matrices = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        MatrixCalculator calc = new MatrixCalculator();
        calc.run();
    }

    /**
     * Main program loop
     */
    public void run() {
        printWelcome();

        while (true) {
            try {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("What would you like to do?");
                System.out.println("=".repeat(60));
                printMainMenu();

                String choice = prompt("> ").trim().toLowerCase();

                if (choice.equals("quit") || choice.equals("exit") || choice.equals("q")) {
                    System.out.println("\nThank you for using Matrix Calculator! Goodbye!");
                    break;
                }

                handleMainMenuChoice(choice);

            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage());
                System.out.println("Don't worry! Try again or type 'help' for assistance.");
            }
        }

        scanner.close();
    }

    private void printWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          MATRIX CALCULATOR v" + VERSION);
        System.out.println("     Your friendly linear algebra companion");
        System.out.println("=".repeat(60));
        System.out.println("\nWelcome! This program helps you perform matrix operations.");
        System.out.println("Perfect for checking homework and learning linear algebra!");
    }

    private void printMainMenu() {
        System.out.println("\n📝 CREATE & MANAGE:");
        System.out.println("  1  - Create a new matrix");
        System.out.println("  2  - View all matrices");
        System.out.println("  3  - View a specific matrix");
        System.out.println("  4  - Delete a matrix");

        System.out.println("\n➕ BASIC OPERATIONS:");
        System.out.println("  5  - Add two matrices");
        System.out.println("  6  - Subtract two matrices");
        System.out.println("  7  - Multiply two matrices");
        System.out.println("  8  - Multiply matrix by scalar");
        System.out.println("  9  - Transpose a matrix");

        System.out.println("\n🔬 ADVANCED OPERATIONS:");
        System.out.println("  10 - Determinant");
        System.out.println("  11 - Trace");
        System.out.println("  12 - RREF (Reduced Row Echelon Form)");
        System.out.println("  13 - Solve system Ax = b");
        System.out.println("  14 - Eigenvalues");
        System.out.println("  15 - Create identity matrix");

        System.out.println("\n❓ HELP:");
        System.out.println("  help - Show detailed help");
        System.out.println("  quit - Exit program");
    }

    private void handleMainMenuChoice(String choice) {
        switch (choice) {
            case "1": createMatrix(); break;
            case "2": viewAllMatrices(); break;
            case "3": viewMatrix(); break;
            case "4": deleteMatrix(); break;
            case "5": addMatrices(); break;
            case "6": subtractMatrices(); break;
            case "7": multiplyMatrices(); break;
            case "8": scaleMatrix(); break;
            case "9": transposeMatrix(); break;
            case "10": calculateDeterminant(); break;
            case "11": calculateTrace(); break;
            case "12": calculateRREF(); break;
            case "13": solveSystem(); break;
            case "14": calculateEigenvalues(); break;
            case "15": createIdentityMatrix(); break;
            case "help": showHelp(); break;
            default:
                System.out.println("\n❌ Invalid choice. Please enter a number from the menu or 'help'.");
        }
    }

    // ==================== CREATE & MANAGE ====================

    private void createMatrix() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("CREATE NEW MATRIX");
        System.out.println("-".repeat(60));

        String name = promptMatrixName("Enter a name for this matrix (e.g., A, B, M1): ");

        if (matrices.containsKey(name)) {
            String confirm = prompt("Matrix '" + name + "' already exists. Overwrite? (yes/no): ");
            if (!confirm.toLowerCase().startsWith("y")) {
                System.out.println("Cancelled.");
                return;
            }
        }

        System.out.println("\nHow would you like to create the matrix?");
        System.out.println("  1 - Enter elements manually");
        System.out.println("  2 - Create zero matrix (specify dimensions)");

        String method = prompt("> ").trim();

        Matrix matrix = null;

        if (method.equals("1")) {
            matrix = createMatrixManual();
        } else if (method.equals("2")) {
            matrix = createZeroMatrix();
        } else {
            System.out.println("Invalid choice. Defaulting to manual entry.");
            matrix = createMatrixManual();
        }

        if (matrix != null) {
            matrices.put(name, matrix);
            System.out.println("\n✅ Matrix '" + name + "' created successfully!");
            System.out.println("\nYour matrix:");
            printMatrix(matrix);
        }
    }

    private Matrix createMatrixManual() {
        int rows = promptPositiveInt("Enter number of rows: ");
        int cols = promptPositiveInt("Enter number of columns: ");

        double[][] data = new double[rows][cols];

        System.out.println("\nEnter matrix elements:");
        System.out.println("(You can enter all elements in one line separated by spaces,");
        System.out.println(" or enter them row by row)");

        for (int i = 0; i < rows; i++) {
            System.out.println("\nRow " + (i + 1) + ":");
            String input = prompt("> ").trim();
            String[] values = input.split("\\s+");

            if (values.length != cols) {
                System.out.println("Expected " + cols + " values, got " + values.length + ". Please re-enter this row.");
                i--;
                continue;
            }

            try {
                for (int j = 0; j < cols; j++) {
                    data[i][j] = Double.parseDouble(values[j]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please re-enter this row.");
                i--;
            }
        }

        return new Matrix(data);
    }

    private Matrix createZeroMatrix() {
        int rows = promptPositiveInt("Enter number of rows: ");
        int cols = promptPositiveInt("Enter number of columns: ");
        return new Matrix(rows, cols);
    }

    private void createIdentityMatrix() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("CREATE IDENTITY MATRIX");
        System.out.println("-".repeat(60));

        String name = promptMatrixName("Enter a name for this identity matrix: ");
        int size = promptPositiveInt("Enter the size (n×n): ");

        Matrix identity = Matrix.identity(size);
        matrices.put(name, identity);

        System.out.println("\n✅ Identity matrix '" + name + "' created successfully!");
        printMatrix(identity);
    }

    private void viewAllMatrices() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("ALL STORED MATRICES");
        System.out.println("-".repeat(60));

        if (matrices.isEmpty()) {
            System.out.println("\nNo matrices stored yet. Create one using option 1!");
            return;
        }

        for (Map.Entry<String, Matrix> entry : matrices.entrySet()) {
            System.out.println("\n📊 Matrix '" + entry.getKey() + "' [" +
                             entry.getValue().getRows() + "×" +
                             entry.getValue().getCols() + "]:");
            printMatrix(entry.getValue());
        }
    }

    private void viewMatrix() {
        if (matrices.isEmpty()) {
            System.out.println("\n❌ No matrices stored yet.");
            return;
        }

        String name = promptExistingMatrixName("Enter matrix name to view: ");
        Matrix m = matrices.get(name);

        System.out.println("\n📊 Matrix '" + name + "' [" + m.getRows() + "×" + m.getCols() + "]:");
        printMatrix(m);
    }

    private void deleteMatrix() {
        if (matrices.isEmpty()) {
            System.out.println("\n❌ No matrices to delete.");
            return;
        }

        String name = promptExistingMatrixName("Enter matrix name to delete: ");
        matrices.remove(name);
        System.out.println("\n✅ Matrix '" + name + "' deleted successfully!");
    }

    // ==================== BASIC OPERATIONS ====================

    private void addMatrices() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("ADD TWO MATRICES");
        System.out.println("-".repeat(60));
        System.out.println("Note: Matrices must have the same dimensions.");

        if (!checkMinimumMatrices(2)) return;

        String name1 = promptExistingMatrixName("Enter first matrix name: ");
        String name2 = promptExistingMatrixName("Enter second matrix name: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name1).add(matrices.get(name2));
        matrices.put(resultName, result);

        System.out.println("\n✅ Result of " + name1 + " + " + name2 + " = " + resultName + ":");
        printMatrix(result);
    }

    private void subtractMatrices() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("SUBTRACT TWO MATRICES");
        System.out.println("-".repeat(60));
        System.out.println("Note: Matrices must have the same dimensions.");

        if (!checkMinimumMatrices(2)) return;

        String name1 = promptExistingMatrixName("Enter first matrix name: ");
        String name2 = promptExistingMatrixName("Enter second matrix name: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name1).subtract(matrices.get(name2));
        matrices.put(resultName, result);

        System.out.println("\n✅ Result of " + name1 + " - " + name2 + " = " + resultName + ":");
        printMatrix(result);
    }

    private void multiplyMatrices() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MULTIPLY TWO MATRICES");
        System.out.println("-".repeat(60));
        System.out.println("Note: Columns of first matrix must equal rows of second matrix.");

        if (!checkMinimumMatrices(2)) return;

        String name1 = promptExistingMatrixName("Enter first matrix name: ");
        String name2 = promptExistingMatrixName("Enter second matrix name: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name1).multiply(matrices.get(name2));
        matrices.put(resultName, result);

        System.out.println("\n✅ Result of " + name1 + " × " + name2 + " = " + resultName + ":");
        printMatrix(result);
    }

    private void scaleMatrix() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MULTIPLY MATRIX BY SCALAR");
        System.out.println("-".repeat(60));

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        double scalar = promptDouble("Enter scalar value: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name).scale(scalar);
        matrices.put(resultName, result);

        System.out.println("\n✅ Result of " + scalar + " × " + name + " = " + resultName + ":");
        printMatrix(result);
    }

    private void transposeMatrix() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("TRANSPOSE MATRIX");
        System.out.println("-".repeat(60));
        System.out.println("Note: Transpose swaps rows and columns.");

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name).transpose();
        matrices.put(resultName, result);

        System.out.println("\n✅ Transpose of " + name + " = " + resultName + ":");
        printMatrix(result);
    }

    // ==================== ADVANCED OPERATIONS ====================

    private void calculateDeterminant() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("CALCULATE DETERMINANT");
        System.out.println("-".repeat(60));
        System.out.println("Note: Matrix must be square (n×n).");

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        Matrix m = matrices.get(name);

        double det = m.determinant();

        System.out.println("\n✅ Determinant of " + name + " = " + det);

        if (Math.abs(det) < 1e-10) {
            System.out.println("📌 Note: Determinant is zero (or very close to zero).");
            System.out.println("   This means the matrix is singular (non-invertible).");
        }
    }

    private void calculateTrace() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("CALCULATE TRACE");
        System.out.println("-".repeat(60));
        System.out.println("Note: Trace is the sum of diagonal elements. Matrix must be square.");

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        Matrix m = matrices.get(name);

        double trace = m.trace();

        System.out.println("\n✅ Trace of " + name + " = " + trace);
    }

    private void calculateRREF() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("REDUCED ROW ECHELON FORM (RREF)");
        System.out.println("-".repeat(60));
        System.out.println("Note: RREF is useful for solving systems and finding matrix rank.");

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        String resultName = promptMatrixName("Enter name for result: ");

        Matrix result = matrices.get(name).rref();
        matrices.put(resultName, result);

        System.out.println("\n✅ RREF of " + name + " = " + resultName + ":");
        printMatrix(result);
    }

    private void solveSystem() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("SOLVE LINEAR SYSTEM Ax = b");
        System.out.println("-".repeat(60));
        System.out.println("Note: A must be square, b must be a column vector.");

        if (!checkMinimumMatrices(2)) return;

        String nameA = promptExistingMatrixName("Enter coefficient matrix name (A): ");
        String nameB = promptExistingMatrixName("Enter right-hand side vector name (b): ");
        String resultName = promptMatrixName("Enter name for solution (x): ");

        Matrix A = matrices.get(nameA);
        Matrix b = matrices.get(nameB);

        System.out.println("\nSolving: " + nameA + " × x = " + nameB);

        Matrix x = A.solve(b);
        matrices.put(resultName, x);

        System.out.println("\n✅ Solution x = " + resultName + ":");
        printMatrix(x);

        // Verification
        System.out.println("\n📌 Verification: " + nameA + " × " + resultName + " should equal " + nameB);
        Matrix verification = A.multiply(x);
        printMatrix(verification);
    }

    private void calculateEigenvalues() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("CALCULATE EIGENVALUES");
        System.out.println("-".repeat(60));
        System.out.println("Note: Matrix must be square. Uses iterative QR algorithm.");

        if (!checkMinimumMatrices(1)) return;

        String name = promptExistingMatrixName("Enter matrix name: ");
        Matrix m = matrices.get(name);

        System.out.println("\n⏳ Computing eigenvalues (this may take a moment)...");

        double[] eigenvalues = m.eigenvalues();

        System.out.println("\n✅ Eigenvalues of " + name + ":");
        for (int i = 0; i < eigenvalues.length; i++) {
            System.out.printf("  λ%d = %.6f\n", i + 1, eigenvalues[i]);
        }

        System.out.println("\n📌 Note: For some matrices, eigenvalues may not converge perfectly.");
    }

    // ==================== HELPER METHODS ====================

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private String promptMatrixName(String message) {
        String name;
        while (true) {
            name = prompt(message).trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Try again.");
            } else if (name.contains(" ")) {
                System.out.println("Name cannot contain spaces. Try again.");
            } else {
                break;
            }
        }
        return name;
    }

    private String promptExistingMatrixName(String message) {
        System.out.println("\nAvailable matrices: " + matrices.keySet());
        String name;
        while (true) {
            name = promptMatrixName(message);
            if (matrices.containsKey(name)) {
                break;
            }
            System.out.println("Matrix '" + name + "' not found. Try again.");
        }
        return name;
    }

    private int promptPositiveInt(String message) {
        while (true) {
            try {
                String input = prompt(message).trim();
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private double promptDouble(String message) {
        while (true) {
            try {
                String input = prompt(message).trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private boolean checkMinimumMatrices(int min) {
        if (matrices.size() < min) {
            System.out.println("\n❌ You need at least " + min + " matri" +
                             (min == 1 ? "x" : "ces") + " for this operation.");
            System.out.println("   Create matrices using option 1.");
            return false;
        }
        return true;
    }

    private void printMatrix(Matrix m) {
        String[] lines = m.toString().split("\n");
        for (String line : lines) {
            System.out.println("  " + line);
        }
    }

    private void showHelp() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MATRIX CALCULATOR - HELP GUIDE");
        System.out.println("=".repeat(60));

        System.out.println("\n📖 GETTING STARTED:");
        System.out.println("  1. Create matrices using option 1");
        System.out.println("  2. Give each matrix a name (like A, B, M1, etc.)");
        System.out.println("  3. Perform operations on your matrices");
        System.out.println("  4. Results can be saved as new matrices");

        System.out.println("\n💡 TIPS:");
        System.out.println("  • Matrix names are case-sensitive (A and a are different)");
        System.out.println("  • You can use decimals (like 1.5, -2.7, 0.333)");
        System.out.println("  • When entering matrix elements, separate with spaces");
        System.out.println("  • Use option 2 to see all your stored matrices");

        System.out.println("\n📐 OPERATION REQUIREMENTS:");
        System.out.println("  • Addition/Subtraction: Matrices must be same size");
        System.out.println("  • Multiplication: Columns of first = Rows of second");
        System.out.println("  • Determinant/Trace: Matrix must be square (n×n)");
        System.out.println("  • Solve Ax=b: A must be square, b must be column vector");

        System.out.println("\n🎓 LINEAR ALGEBRA CONCEPTS:");
        System.out.println("  • Transpose: Flips rows and columns");
        System.out.println("  • Determinant: Zero means matrix is not invertible");
        System.out.println("  • Trace: Sum of diagonal elements");
        System.out.println("  • RREF: Simplest form for solving systems");
        System.out.println("  • Eigenvalues: Special scalars λ where Av = λv");

        System.out.println("\n❓ NEED MORE HELP?");
        System.out.println("  • Check your linear algebra textbook");
        System.out.println("  • Ask your TA or professor");
        System.out.println("  • Remember: This tool is for checking work, not replacing learning!");
    }
}
