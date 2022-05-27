import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int makePort = 6666;
    final static int cancelPort = 6667;


    // this example provides many client can communicate with server at the same time, print message at client

    public static void main(String[] args) throws IOException {
        Hospital hospital = new Hospital();
        Hospital.printDoctors();
        System.out.println("Hospital is  Started ");
        new Thread() {
            public void run() {
                try {
                    ServerSocket Socket = new ServerSocket(makePort);
                    while (true) {
                        new Appointment(Socket.accept(), hospital).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    ServerSocket Socket = new ServerSocket(cancelPort);
                    while (true) {
                        new Appointment(Socket.accept(), hospital).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static class Appointment extends Thread {
        Socket socket;
        Hospital hospital;

        public Appointment(Socket socket, Hospital hos) {
            this.socket = socket;
            this.hospital = hos;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); // input to server which came from client
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true); // out message from server to client
                String str = "";
                while ((str = in.readLine()) != null) {
                    String[] tokens = str.split(" ");
                    String patientName = tokens[0];
                    int id = Integer.parseInt(tokens[1]);
                    int ts = Integer.parseInt(tokens[2]);
                    System.out.println("Name " + patientName + " Id: " + id + " ts: " + ts);
                    if (this.socket.getLocalPort() == makePort) {
                        out.println(this.hospital.makeAppointment(id, ts, patientName));// out from server to client
                    } else {
                        out.println(this.hospital.cancelAppointment(id, ts, patientName));
                    }
                    System.out.println();
                    Hospital.printDoctors();
                    System.out.println();
                }
                out.close();
                in.close();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
