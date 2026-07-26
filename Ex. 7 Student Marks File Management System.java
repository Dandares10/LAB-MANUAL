//Program:

//Ex. 7 Design a Student Marks File Management System that stores, retrieves, and updates student marks using Java File Handling

import java.io.*;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Details of 3 Students\n");
        
        // Add 3 student records
        for (int i = 1; i <= 3; i++) {
            System.out.println("Student " + i);
            System.out.print("Roll No: ");
            String roll = scanner.nextLine().trim();
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Marks: ");
            double marks;
            try {
                marks = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid marks input. Please enter a valid number.");
                i--; // Retry this student
                continue;
            }
            StudentMarksManager.addStudentRecord(roll, name, marks);
            System.out.println();
        }
        
        System.out.println("Student records saved successfully.\n");
        
        // Display all records
        System.out.println("Student Records");
        System.out.println("-----");
        StudentMarksManager.displayAllRecords();
        
        // Search for a student
        System.out.print("Enter Roll Number to Search: ");
        String searchRoll = scanner.nextLine().trim();
        StudentMarksManager.searchStudentRecord(searchRoll);
        
        scanner.close();
    }
}


class StudentMarksManager {
    private static final String FILE_NAME = "students_marks.txt";

    // 1. CREATE: Add a new student mark record
    public static void addStudentRecord(String rollNum, String name, double marks) {
        if (recordExists(rollNum)) {
            System.out.println("❌ Error: A student record with Roll Number " + rollNum + " already exists.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            Student student = new Student(rollNum, name, marks);
            writer.write(student.toCsvString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("❌ Fatal Error writing to database file: " + e.getMessage());
        }
    }

    // 2. READ: Display all stored student marks
    public static void displayAllRecords() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            System.out.println("ℹ️ No records found. The database file is currently empty.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    System.out.println(data[0] + ", " + data[1] + ", " + data[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading data from file: " + e.getMessage());
        }
    }

    // 3. RETRIEVE: Search for a single specific student
    public static void searchStudentRecord(String rollNum) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(rollNum)) {
                    System.out.println("\nStudent Found");
                    System.out.println("Roll No: " + data[0]);
                    System.out.println("Name: " + data[1]);
                    System.out.println("Marks: " + data[2]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("❌ No student record matching Roll Number '" + rollNum + "' was located.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error searching file: " + e.getMessage());
        }
    }

    // 4. UPDATE: Modify a student's marks using a file swapping strategy
    public static void updateStudentMarks(String rollNum, double newMarks) {
        File dbFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(rollNum)) {
                    // Write modified marks data row
                    writer.write(data[0] + "," + data[1] + "," + newMarks);
                    updated = true;
                } else {
                    // Write unmodified row
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error rewriting database files: " + e.getMessage());
            return;
        }

        // Finalize transaction by overwriting main data file with modified data file
        if (updated) {
            if (dbFile.delete() && tempFile.renameTo(dbFile)) {
                System.out.println("✅ Student marks updated safely inside file database!");
            } else {
                System.out.println("❌ Critical File Processing Exception: Could not finalize modifications.");
            }
        } else {
            tempFile.delete(); // Delete the temporary file if nothing was altered
            System.out.println("❌ Update aborted. Student matching Roll Number '" + rollNum + "' does not exist.");
        }
    }

    // Internal check helper function to guarantee record integrity
    private static boolean recordExists(String rollNum) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(rollNum)) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }
}


class Student {
    private String rollNumber;
    private String name;
    private double marks;

    public Student(String rollNumber, String name, double marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks = marks;
    }

    // Getters and Setters
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return "Roll No: " + rollNumber + " | Name: " + name + " | Marks: " + marks;
    }

    // Convert object data to a CSV record format line
    public String toCsvString() {
        return rollNumber + "," + name + "," + marks;
    }
}
