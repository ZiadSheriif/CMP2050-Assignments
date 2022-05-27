import java.util.ArrayList;
import java.util.List;

public class Doctor {
    protected int id;
    protected String name;
    protected List<Boolean> timeSlots;
    protected List<String> patients;

    public Doctor(int id, String name, List<Boolean> timeslots, List<String> patient) {
        this.id = id;
        this.name = name;
        this.timeSlots = timeslots;
        this.patients = patient;
    }

}
