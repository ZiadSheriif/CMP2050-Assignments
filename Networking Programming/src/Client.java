import javax.xml.transform.Result;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    final static int reservingPort = 6666;
    final static int cancellationPort = 6667;


    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.print("Enter Your Name (Patient's Name): ");
        Scanner scan = new Scanner(System.in);
        String NamePatient = scan.next();
        System.out.println();
//        System.out.print("Making Appointment(1) :::: CancellationAppointment(2): ");
//        int port = scan.nextInt() == 1 ? reservingPort : cancellationPort;
        Socket orderSocket = new Socket("localhost", reservingPort);
        Socket orderSocket2 = new Socket("localhost", cancellationPort);


        // auto flush ==> print message immediately once call out function
        PrintWriter out = new PrintWriter(orderSocket.getOutputStream(), true); //out from client ==> input to server
        PrintWriter out1 = new PrintWriter(orderSocket2.getOutputStream(), true); //out from client ==> input to server
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); // input from client from console (user input)
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(orderSocket.getInputStream())); // input to client after conversion from server(out from server)
        BufferedReader socketReader1 = new BufferedReader(new InputStreamReader(orderSocket2.getInputStream())); // input to client after conversion from server(out from server)

        String request = "";
//        BufferedReader readData = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Making Appointment(1) :::: CancellationAppointment(2): ");
        while ((request = consoleReader.readLine()) != null) {
            int port = request.equals("1") ? reservingPort : cancellationPort;
            System.out.println();
            System.out.print("Enter Doctor's ID:");
            int ID = scan.nextInt();
            System.out.println();
            System.out.print("Enter timeslot Index:");
            int Ts = scan.nextInt();
            System.out.println();
            if (port == reservingPort) {
                out.println(NamePatient + " " + ID + " " + Ts);
                String Result = socketReader.readLine(); // receive message after operation from server
                System.out.println("Get from Server to Making : " + Result);
            } else {
                out1.println(NamePatient + " " + ID + " " + Ts);
                String Result = socketReader1.readLine(); // receive message after operation from server
                System.out.println("Get from Server to Cancel : " + Result);
            }
            System.out.println();
            System.out.println("===============================================================");
            System.out.println();
            System.out.print("Making Appointment(1) :::: CancellationAppointment(2): ");
        }
        out.close();
        out1.close();
        scan.close();
        orderSocket.close();
        orderSocket2.close();
    }
}

