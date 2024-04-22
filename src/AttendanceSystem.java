import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceSystem {
    private Map<Integer, Student> students;
    private Map<Integer, Map<LocalDateTime, Boolean>> attendanceRecords;
    private static final String FILE_NAME = "attendance_records.txt";

    public AttendanceSystem() {

        students = new HashMap<>();

        attendanceRecords = new HashMap<>();

        loadAttendanceRecords();
    }


    public void addStudent(int id, String name) {

        students.put(id, new Student(id, name));

        attendanceRecords.put(id, new HashMap<>());
    }


    public void markAttendance(int studentId, boolean isPresent) {

        if (!students.containsKey(studentId)) {

            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }

        Map<LocalDateTime, Boolean> studentAttendance = attendanceRecords.get(studentId);

        studentAttendance.put(LocalDateTime.now(), isPresent);

        attendanceRecords.put(studentId, studentAttendance);

        saveAttendanceRecords();

        System.out.println("Attendance marked for Student ID " + studentId);
    }


    public void displayAttendance() {

        System.out.println("Attendance Records:");

        for (Map.Entry<Integer, Map<LocalDateTime, Boolean>> entry : attendanceRecords.entrySet()) {

            Student student = students.get(entry.getKey());

            System.out.println("Student ID: " + student.getId() + ", Name: " + student.getName());

            for (Map.Entry<LocalDateTime, Boolean> attendance : entry.getValue().entrySet()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                System.out.println("\tDate/Time: " + attendance.getKey().format(formatter) + ", Status: " +
                        (attendance.getValue() ? "Present" : "Absent"));

            }
        }
    }


    private void loadAttendanceRecords() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int studentId = Integer.parseInt(parts[0]);

                boolean isPresent = Boolean.parseBoolean(parts[1]);

                LocalDateTime dateTime = LocalDateTime.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                if (!attendanceRecords.containsKey(studentId)) {

                    attendanceRecords.put(studentId, new HashMap<>());

                }
                attendanceRecords.get(studentId).put(dateTime, isPresent);
            }
        } catch (IOException e) {

            System.out.println("Error loading attendance records: " + e.getMessage());
        }
    }

    private void saveAttendanceRecords() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Map.Entry<Integer, Map<LocalDateTime, Boolean>> entry : attendanceRecords.entrySet()) {

                int studentId = entry.getKey();

                for (Map.Entry<LocalDateTime, Boolean> attendance : entry.getValue().entrySet()) {

                    LocalDateTime dateTime = attendance.getKey();

                    boolean isPresent = attendance.getValue();

                    writer.write(studentId + "," + isPresent + "," + dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                    writer.newLine();
                }
            }
        } catch (IOException e) {

            System.out.println("Error saving attendance records: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AttendanceSystem attendanceSystem = new AttendanceSystem();

        System.out.println("Welcome to the Attendance System");


        attendanceSystem.addStudent(20511, "John Doe");

        attendanceSystem.addStudent(27649, "Jane Smith");

        attendanceSystem.addStudent(78412, "Alice Johnson");

        attendanceSystem.addStudent(69142, "Monica White");

        attendanceSystem.addStudent(14023, "Jhon Welton");

        attendanceSystem.addStudent(47236, "Steph Mendy");

        attendanceSystem.addStudent(10247, "Sussan Margeth");

        attendanceSystem.addStudent(78963, "Jessica Brown");

        attendanceSystem.addStudent(14769, "Bob Williams");

        attendanceSystem.addStudent(65989, "Jude Berdong");

        while (true) {
            System.out.println("\nMenu:");

            System.out.println("1. Mark Attendance");

            System.out.println("2. Display Attendance Records");

            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");

            int choice;

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a number.");

                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter student ID: ");

                    int studentId;

                    try {

                        studentId = Integer.parseInt(scanner.nextLine());

                    }
                    catch (NumberFormatException e) {

                        System.out.println("Invalid student ID. Please enter a number.");

                        continue;
                    }
                    System.out.print("Is the student present? (true/false): ");

                    boolean isPresent;

                    try {

                        isPresent = Boolean.parseBoolean(scanner.nextLine());

                    }
                    catch (IllegalArgumentException e) {

                        System.out.println("Invalid input. Please enter true or false.");

                        continue;
                    }
                    attendanceSystem.markAttendance(studentId, isPresent);

                    break;

                case 2:

                    attendanceSystem.displayAttendance();

                    break;
                case 3:

                    System.out.println("Exiting program");

                    System.exit(0);
                default:

                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}



