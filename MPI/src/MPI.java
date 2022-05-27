//import mpi.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MPIMatrix {
    public static void main(String[] args) throws IOException {
        MPI.Init(args); //beginning
        int myRank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        System.out.println("Process: " + myRank + "   <==|||||==>   Size = " + size);
        int[][] Matrix = new int[3][3];
        int[] det = new int[1]; //for each process
        if (myRank == 0) { //by processes 0
            readFile("input", Matrix); //reading inputs
            print(Matrix);
        }
        //sending data to all processes
        for (int i = 0; i < 3; i++) {
            MPI.COMM_WORLD.Bcast(Matrix[i], 0, 3, MPI.INT, 0);
        }
        if (myRank != 0) // rest of processes
            det[0] = determinant(Matrix, myRank);
        // receive calc of each process (summation result)
        MPI.COMM_WORLD.Reduce(det, 0, det, 0, 1, MPI.INT, MPI.SUM, 0);

        if (myRank == 0) {
            System.out.println();
            System.out.println("Determinant: " + det[0]);
            if (det[0] != 0) {
                System.out.println("Invertible Matrix");
            } else {
                System.out.println("Singular Matrix");
            }
        }
        MPI.Finalize();
    }

    public static void print(int[][] Matrix) {
        System.out.println("*******************************");
        System.out.println("Whole Matrix");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(Matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("*******************************");
    }

    private static void readFile(String e, int[][] Matrix) throws IOException {
        File file = new File(e);
        Scanner reader = new Scanner(file);
        int i = 0, j;
        while (reader.hasNextLine()) {
            j = 0;
            String r = reader.nextLine();
            for (String line : r.split(" ")) {
                Matrix[i][j] = Integer.parseInt(line);
                j++;
            }
            i++;
        }
        reader.close();
    }

    private static int determinant(int[][] matrix, int myRank) {
        int col = myRank - 1, det = 0, z = 0;
        int[] temp = new int[4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 0 && j != col) {
                    temp[z] = matrix[i][j];
                    z++;
                }
            }
        }
        det = matrix[0][col] * (temp[0] * temp[3] - temp[1] * temp[2]);
        if (col == 1) {
            det = -1 * det;
        }
        System.out.println("Rank " + myRank + ": " + det);
        return det;
    }

}