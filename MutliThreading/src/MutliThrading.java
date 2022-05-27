import java.util.Scanner;

class Multiply implements Runnable {
    private int[][] arrA;
    private int[][] arrB;
    private int[][] arrC;
    private int rowA, colA, rowB, colB;

    Multiply(int[][] A, int[][] B, int[][] result) {
        this.arrA = A;
        this.arrB = B;
        this.arrC = result;
        this.rowA = A.length;
        this.colA = A[0].length;
        this.rowB = B.length;
        this.colB = B[0].length;
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName() == "1") {
            for (int i = 0; i < rowA; i++) {
                for (int j = 0; j < colB / 2; j++) {
                    for (int z = 0; z < rowB; z++)
                        arrC[i][j] += arrA[i][z] * arrB[z][j];
                    System.out.println("row = " + i + " col = " + j + "  Is Done By Thread " + Thread.currentThread().getName());
                }
            }

        } else if (Thread.currentThread().getName() == "2") {
            for (int i = 0; i < rowA; i++) {
                for (int j = colB / 2; j < colB; j++) {
                    for (int z = 0; z < rowB; z++)
                        arrC[i][j] += arrA[i][z] * arrB[z][j];
                    System.out.println("row = " + i + " col = " + j + "  Is Done By Thread " + Thread.currentThread().getName());
                }

            }
        }
    }

    public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

class main {
    public static void main(String[] args) throws InterruptedException {
        int rA, rB, cA, cB, input;
        int[][] A;
        int[][] B;
        int[][] C;
        Scanner sc = new Scanner(System.in);
        System.out.print("Row of Matrix A: ");
        rA = sc.nextInt();
        System.out.print("Col of Matrix A: ");
        cA = sc.nextInt();
        System.out.print("Row of Matrix B: ");
        rB = sc.nextInt();
        System.out.print("Col of Matrix B: ");
        cB = sc.nextInt();
        System.out.println();
        if (cA != rB) {
            System.out.println("Can't Multiply Matrix A by Matrix B");
            return;
        }
        System.out.println("The Matrix Result from product will be " + rA + " x " + cB);
        System.out.println();
        A = new int[rA][cA];
        B = new int[rB][cB];
        C = new int[rA][cB];
        System.out.println("Insert Matrix A: ");
        System.out.println();
        for (int i = 0; i < rA; i++) {
            for (int j = 0; j < cA; j++) {
                System.out.print(i + "," + j + " = ");
                A[i][j] = sc.nextInt();
            }
        }
        System.out.println();
        System.out.println("Insert Matrix B: ");
        System.out.println();
        for (int i = 0; i < rB; i++) {
            for (int j = 0; j < cB; j++) {
                System.out.print(i + "," + j + " = ");
                B[i][j] = sc.nextInt();
            }
        }
        Runnable R1 = new Multiply(A, B, C);
        Runnable R2 = new Multiply(A, B, C);
//        Multiply[][] M = new Multiply[rA][cB];
        Thread t1 = new Thread(R1);
        t1.setName("1");
        Thread t2 = new Thread(R2);
        t2.setName("2");
        System.out.println();
        t1.start();
        t2.start();

        System.out.println("Matrix A");
        Multiply.print(A);
        System.out.println("Matrix B");
        Multiply.print(B);
        System.out.println();
        t1.join();
        t2.join();
        System.out.println();
        System.out.println("Result of Product Matix A x B");
        Multiply.print(C);
    }

}
