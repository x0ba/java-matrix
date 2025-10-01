public class Test {
  public static void main(String[] args) {
    double[][] matrix1 = {{4, 5, 6}, {23, 13, 23}};
    double[][] matrix2 = {{2.3, 4.2, 1.3}, {18, 13, 19}};

    Matrix a = new Matrix(matrix1);
    Matrix b = new Matrix(matrix2);

    System.out.println(a);
    System.out.println(b);
  }
}
