package y129;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/HMS";

    static String USER = "root";
    static String PASS = "1234";

    static Connection conn = null;
    static Statement stmt = null;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt =  conn.createStatement();
            displayMenu();
            while (true) {
                System.out.println("Enter your choice:");
                int choice = Integer.parseInt(scanner.nextLine());
                handleUserInput(choice);
                if(choice == 0) {
                	break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("1. Add Patient");
        System.out.println("2. Display All Patients");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Add Appointment");
        System.out.println("6. Display All Appointments");
        System.out.println("7. Display All Staffs");
        System.out.println("8. Display All Departments");
        System.out.println("9. Update Appointment");
        System.out.println("10. Delete Appointment");
        System.out.println("0. Exit");
    }

    private static void handleUserInput(int choice) throws SQLException {
        switch (choice) {
            case 1:
                addPatient();
                break;
            case 2:
                displayAllPatients();
                break;
            case 3:
                updatePatient();
                break;
            case 4:
                deletePatient();
                break;
            case 5:
                addAppointment();
                break;
            case 6:
                displayAllAppointments();
                break;
            case 7:
                displayAllStaffs();
                break;
            case 8:
                displayAllDepartments();
                break;  
            case 9:
                updateAppointment();
                break;
            case 10:
                deleteAppointment();
                break; 
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
                break;
        }
    }

    private static void addPatient() throws SQLException {
        System.out.println("Enter patient name:");
        String name = scanner.nextLine();
        System.out.println("Enter patient contact details:");
        String contactDetails = scanner.nextLine();

        String sql = "INSERT INTO Patients (Name, ContactDetails) VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, contactDetails);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Patient added successfully!");
        } else {
            System.out.println("Failed to add patient.");
        }
    }

    private static void displayAllPatients() throws SQLException {
        String sql = "SELECT * FROM Patients";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println("Patient ID: " + rs.getInt("PatientID") +
                    ", Name: " + rs.getString("Name") +
                    ", Contact Details: " + rs.getString("ContactDetails"));
        }
        rs.close();
    }

    private static void updatePatient() throws SQLException {
        System.out.println("Enter the patient ID to update:");
        int patientID = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new patient name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new patient contact details:");
        String newContactDetails = scanner.nextLine();

        String sql = "UPDATE Patients SET Name=?, ContactDetails=? WHERE PatientID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, newName);
        preparedStatement.setString(2, newContactDetails);
        preparedStatement.setInt(3, patientID);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Patient updated successfully!");
        } else {
            System.out.println("Failed to update patient.");
        }
    }

    private static void deletePatient() throws SQLException {
    	System.out.println("Enter the patient ID to delete:");
        int patientID = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM Patients WHERE PatientID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, patientID);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Patient deleted successfully!");
        } else {
            System.out.println("Failed to delete patient. Patient ID not found.");
        }
    }

    private static void addAppointment() throws SQLException {
    	System.out.println("Enter appointment date and time (YYYY-MM-DD HH:MM:SS):");
        String dateTime = scanner.nextLine();

        System.out.println("Enter patient ID:");
        int patientID = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter staff ID:");
        int staffID = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO Appointments (Date, PatientID, StaffID) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, dateTime);
        preparedStatement.setInt(2, patientID);
        preparedStatement.setInt(3, staffID);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Appointment added successfully!");
        } else {
            System.out.println("Failed to add appointment.");
        }
    }

    private static void displayAllAppointments() throws SQLException {
    	String sql = "SELECT * FROM Appointments";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int appointmentID = rs.getInt("AppointmentID");
            Timestamp date = rs.getTimestamp("Date");
            int patientID = rs.getInt("PatientID");
            int staffID = rs.getInt("StaffID");
            System.out.println("Appointment ID: " + appointmentID +
                    ", Date: " + date +
                    ", Patient ID: " + patientID +
                    ", Staff ID: " + staffID);
        }
        rs.close();
    }
    
    private static void displayAllStaffs() throws SQLException {
    	String sql = "SELECT * FROM staff";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int staffID = rs.getInt("staffID");
            String Name = rs.getString("Name");
            String Role = rs.getString("Role");
            int departmentID = rs.getInt("DepartmentID");
            System.out.println("Staff ID: " + staffID +
                    ", Name : " + Name +
                    ", Role : " + Role+
                    ", Department ID : " + departmentID);
        }
        rs.close();
    }
    
    private static void displayAllDepartments() throws SQLException {
    	String sql = "SELECT * FROM Departments";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int depID = rs.getInt("DepartmentID");
            String Name = rs.getString("Name");
            System.out.println("Staff ID: " + depID +
                    ", Name : " + Name );
        }
        rs.close();
    }
    
    private static void updateAppointment() throws SQLException {
        System.out.println("Enter the appointment ID to update:");
        int appointmentID = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new appointment date and time (YYYY-MM-DD HH:MM:SS):");
        String newDateTime = scanner.nextLine();

        String sql = "UPDATE Appointments SET Date=? WHERE AppointmentID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, newDateTime);
        preparedStatement.setInt(2, appointmentID);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Appointment updated successfully!");
        } else {
            System.out.println("Failed to update appointment. Appointment ID not found.");
        }
    }

    private static void deleteAppointment() throws SQLException {
        System.out.println("Enter the appointment ID to delete:");
        int appointmentID = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM Appointments WHERE AppointmentID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, appointmentID);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Appointment deleted successfully!");
        } else {
            System.out.println("Failed to delete appointment. Appointment ID not found.");
        }
    }

}
