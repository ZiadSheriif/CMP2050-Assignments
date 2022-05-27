import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hospital {
    static List<Doctor> doctors = new ArrayList<>();

    Hospital() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
        String readLine;
        while ((readLine = reader.readLine()) != null) {
            String[] tokens = readLine.split(" ");
            List<Boolean> ts = new ArrayList<Boolean>(Collections.nCopies(Integer.parseInt(tokens[2]), false));
            List<String> patients = new ArrayList<>(Collections.nCopies(Integer.parseInt(tokens[2]), ""));
            doctors.add(new Doctor(Integer.parseInt(tokens[0]), tokens[1], ts, patients));
        }
    }

    static String makeAppointment(int id, int index, String patName) {
        for (Doctor doc : doctors) {
            if (doc.id == id) {
                if (doc.timeSlots.size() < index || index < 1)
                    return "the timeslot index is out of boundary";
                if (!doc.timeSlots.get(index - 1)) {
                    doc.timeSlots.set(index - 1, true);
                    doc.patients.set(index - 1, patName);
                    return "Making the Appointment is done Successfully";
                } else
                    return "the doctor is already busy at this timeslot";
            }
        }
        return "The Doctor ID is Not Found in Hospital";
    }

    static String cancelAppointment(int id, int index, String patName) {
        for (Doctor doc : doctors) {
            if (doc.id == id) {
                if (doc.timeSlots.size() < index || index < 1) {
                    return "the timeslot index is out of boundary";
                }
                if (doc.timeSlots.get(index - 1)) {
                    if (doc.patients.get(index - 1).equals(patName)) {
                        doc.timeSlots.set(index - 1, false);
                        doc.patients.set(index - 1, "");
                        return "Cancelling the appointment is done successfully ";
                    } else
                        return "the doctor has an appointment to a different patient name at this timeslot";
                } else
                    return "the doctor doesn't have an appointment at this timeslot";
            }
        }
        return "The Doctor ID is Not Found in Hospital";
    }

    static void printDoctors() {
        for (Doctor doc : doctors) {
            System.out.println("ID: " + doc.id + " Name: " + doc.name + " Time Slots: " + doc.timeSlots + " Patients: " + doc.patients);
        }
    }

    public static void main(String[] args) throws IOException {
//        new Hospital("test.txt");
        printDoctors();
    }
}
