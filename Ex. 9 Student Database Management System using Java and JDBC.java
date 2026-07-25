
//Program:

//Ex. 9 Design a Student Database Management System that performs CRUD operations on student records using Java and JDBC (Java Practice - Student Database Management System).

package school;

import java.util.List;
import java.util.Scanner;

public class MainApplication {
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final Scanner scanner = new Scanner(System.util.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== STUDENT DATABASE MANAGEMENT SYSTEM ===");
            System.out.println("1. Create (Add Student)");
            System.out.println("2. Read (View All Students)");
            System.out.println("3. Update Student Record");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1: handleCreate(); break;
                case 2: handleRead(); break;
                case 3: handleUpdate(); break;
                case 4: handleDelete(); break;
                case 5: 
                    System.out.println("Closing connections. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default: 
                    System.out.println("Invalid entry. Try choices 1 to 5.");
            }
        }
    }

    private static void handleCreate() {
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Enrolled Course: ");
        String course = scanner.nextLine();

        Student student = new Student(name, email, age, course);
        if (studentDAO.addStudent(student)) {
            System.out.println("✔ Student profile committed successfully!");
        }
    }

    private static void handleRead() {
        List<Student> list = studentDAO.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No matching rows located in database table.");
            return;
        }
        System.out.printf("\n%-6s | %-20s | %-25s | %-5s | %-15s\n", "ID", "Name", "Email", "Age", "Course");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student s : list) {
            System.out.printf("%-6d | %-20s | %-25s | %-5d | %-15s\n", s.getId(), s.getName(), s.getEmail(), s.getAge(), s.getCourse());
        }
    }

    private static void handleUpdate() {
        System.out.print("Specify target Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter Updated Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Updated Email Address: ");
        String email = scanner.nextLine();
        System.out.print("Enter Updated Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Updated Enrolled Course: ");
        String course = scanner.nextLine();

        Student student = new Student(id, name, email, age, course);
        if (studentDAO.updateStudent(student)) {
            System.out.println("✔ Student payload successfully altered.");
        } else {
            System.out.println("❌ Entry modification failed. Verify target Key ID.");
        }
    }

    private static void handleDelete() {
        System.out.print("Specify target Student ID to erase: ");
        int id = scanner.nextInt();
        
        if (studentDAO.deleteStudent(id)) {
            System.out.println("✔ Target row cleanly dropped from memory context.");
        } else {
            System.out.println("❌ Dropping target entry failed. Check entry indexes.");
        }
    }
}


package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE Operation
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (name, email, age, course) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // READ Operation (Get All Records)
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getString("course")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    // UPDATE Operation
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET name = ?, email = ?, age = ?, course = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());
            pstmt.setInt(5, student.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // DELETE Operation
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
}


package school;

public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private String course;

    public Student(String name, String email, int age, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    public Student(int id, String name, String email, int age, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
}

//DB Connection Class

package school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3000/StudentDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_secure_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

DB Setup

CREATE DATABASE StudentDB;
USE StudentDB;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT NOT NULL,
    course VARCHAR(100) NOT NULL
);
