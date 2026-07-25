
//Program:

//Ex. 10 Design and develop a Student Record Management System using ArrayList and HashMap to perform Add, Update, Search, Delete, and Display operations on student records

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentManagement system = new StudentManagement();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== STUDENT RECORD MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1: // Add Student
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    
                    int age = -1;
                    while (age < 0) {
                        System.out.print("Enter Age: ");
                        try {
                            age = Integer.parseInt(scanner.nextLine());
                            if (age < 0) System.out.println("Age cannot be negative.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid age format. Enter a number.");
                        }
                    }
                    
                    System.out.print("Enter Course: ");
                    String course = scanner.nextLine().trim();

                    Student newStudent = new Student(id, name, age, course);
                    if (system.addStudent(newStudent)) {
                        System.out.println("Success: Student added successfully!");
                    } else {
                        System.out.println("Error: Student ID already exists.");
                    }
                    break;

                case 2: // Update Student
                    System.out.print("Enter Student ID to update: ");
                    String updateId = scanner.nextLine().trim();
                    Student existing = system.searchStudent(updateId);
                    
                    if (existing == null) {
                        System.out.println("Error: Student record not found.");
                        break;
                    }

                    System.out.print("Enter New Name (Current: " + existing.toString() + "): ");
                    // For a proper update, ensure you have implemented setters in Step 1
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine().trim();
                    
                    int newAge = -1;
                    while (newAge < 0) {
                        System.out.print("Enter New Age: ");
                        try {
                            newAge = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid format. Enter a number.");
                        }
                    }
                    
                    System.out.print("Enter New Course: ");
                    String newCourse = scanner.nextLine().trim();

                    if (system.updateStudent(updateId, newName, newAge, newCourse)) {
                        System.out.println("Success: Student record updated!");
                    } else {
                        System.out.println("Error: Update failed.");
                    }
                    break;

                case 3: // Search Student
                    System.out.print("Enter Student ID to search: ");
                    String searchId = scanner.nextLine().trim();
                    Student foundStudent = system.searchStudent(searchId);
                    
                    if (foundStudent != null) {
                        System.out.println("\nRecord Found:");
                        System.out.println(foundStudent);
                    } else {
                        System.out.println("Error: Student not found.");
                    }
                    break;

                case 4: // Delete Student
                    System.out.print("Enter Student ID to delete: ");
                    String deleteId = scanner.nextLine().trim();
                    
                    if (system.deleteStudent(deleteId)) {
                        System.out.println("Success: Student record deleted.");
                    } else {
                        System.out.println("Error: Student not found.");
                    }
                    break;

                case 5: // Display All Students
                    List<Student> students = system.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No student records available.");
                    } else {
                        System.out.println("\n--- All Student Records ---");
                        for (Student s : students) {
                            System.out.println(s);
                        }
                    }
                    break;

                case 6: // Exit
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Choose option 1 to 6.");
            }
        }
    }
}


public class Student {
    private String studentId, name, course;
    private int age;

    public Student(String studentId, String name, int age, String course) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.course = course;
    }
    // Getters, Setters, and toString() method for display
    public String getStudentId() { return studentId; }
    @Override
    public String toString() { return "ID: " + studentId + " | Name: " + name + " | Age: " + age + " | Course: " + course; }
    // Add setters for name, age, course here
   public void setName(String name) { this.name = name; }
   public void setAge(int age) { this.age = age; }
   public void setCourse(String course) { this.course =      course; }
}

import java.util.*;

public class StudentManagement {
    private HashMap<String, Student> studentMap = new HashMap<>();

    public boolean addStudent(Student s) {
        if (studentMap.containsKey(s.getStudentId())) return false;
        studentMap.put(s.getStudentId(), s);
        return true;
    }
    public Student searchStudent(String id) { return studentMap.get(id); }
    public boolean updateStudent(String id, String name, int age, String course) {
        if (!studentMap.containsKey(id)) return false;
        Student s = studentMap.get(id);
        s.setName(name); s.setAge(age); s.setCourse(course);
        return true;
    }
    public boolean deleteStudent(String id) { return studentMap.remove(id) != null; }
    public List<Student> getAllStudents() { return new ArrayList<>(studentMap.values()); }
}
