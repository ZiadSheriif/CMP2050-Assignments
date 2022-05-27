import java.util.Scanner;

interface Addable {

    Object Add(Object obj);
}

class Matrix implements Addable {
  protected  int N, M;
    protected int[][] Numbers;

    public Matrix(int ro, int co) {
        M = ro;
        N = co;
        Numbers = new int[ro][co];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                Numbers[i][j] = -3;
            }
        }
    }

    @Override
    public Object Add(Object mat) {
        Object s = new Matrix(this.M, this.N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                ((Matrix) s).Numbers[i][j] = this.Numbers[i][j] + ((Matrix) mat).Numbers[i][j];
            }
        }
        return s;
    }

    public boolean SetNumbers(int[] arr) {
        // int input;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // Scanner sc = new Scanner(System.in);
                // input = sc.nextInt();
                Numbers[i][j] = arr[i * N + j];
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (Numbers[i][j] == -3)
                    return false;
            }
        }
        return true;
    }

    public void Transpose() {
        int[][] arrTemp = new int[N][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                arrTemp[j][i] = Numbers[i][j];
            }
        }
        int temp = this.M;
        this.M = this.N;
        this.N = temp;
        Numbers = arrTemp;
    }
    public void print() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(Numbers[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class IdentityMatrix extends Matrix {

    IdentityMatrix(int M, int N) {
        super(M, N);
    }

    @Override
    public boolean SetNumbers(int[] arr) {
        if (super.SetNumbers(arr)) {
            if (M == N) {
                for (int i = 0; i < M; i++) {
                    for (int j = 0; j < N; j++) {
                        if (i == j && Numbers[i][j] != 1)
                            return false;
                        else if (i != j && Numbers[i][j] != 0)
                            return false;
                    }
                }
            } else
                return false;
        }
        return true;
    }

    @Override
    public void Transpose() {
        super.Transpose();
    }

    public static void main(String[] args) {
        int[] Arr1 = new int[] { 1, 2, 3, 4, 5, 6 };
        int[] Arr2 = new int[] { 7, 3, 9, 4, 11, 41 };
        int[] Arr3 = new int[] { 1, 0, 2, 0, 1, 0, 0, 0, 1 };
        Matrix m1 = new Matrix(3, 2);
        m1.SetNumbers(Arr1);
        System.out.println("Matrix 1:");
        m1.print();
        Matrix m2 = new Matrix(3, 2);
        m2.SetNumbers(Arr2);
        System.out.println("Matrix 2:");
        m2.print();
        Object Sum = m1.Add(m2);
        System.out.println("Sum Matrix ");
        ((Matrix) Sum).print();
        System.out.println("Tranpose of Matrix 1:");
        m1.Transpose();
        m1.print();
        System.out.println("Tranpose of Matrix 2:");
        m2.Transpose();
        m2.print();
        IdentityMatrix Im = new IdentityMatrix(3, 3);
        if (Im.SetNumbers(Arr3) == true)
            System.out.println("Verified Indentity Matrix");
        else
            System.out.println("Unverified Identity Matrix:");
        Im.print();
        System.out.println("Tranpose of Identity Matrix:");
        Im.Transpose();
        Im.print();

    }
}