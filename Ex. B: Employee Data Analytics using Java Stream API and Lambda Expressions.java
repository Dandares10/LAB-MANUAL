//Ex. B Design and implement an Employee Data Analytics program that uses Java Stream API and Lambda Expressions to filter, sort, group, and summarize employee data

//Program:

package analytics;

import java.util.List;

public record Employee(
    int id,
    String name,
    String department,
    double salary
) {}

package analytics;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalytics {

    public static void main(String[] args) {
        List<Employee> employees = getSampleEmployees();

        System.out.println("---- All Employees ----");
        employees.forEach(e -> System.out.println(e.id()));

        System.out.println("\n---- Employees Details ----");
        employees.forEach(e -> System.out.println(e.getName() + " " + e.getDepartment() + " " + e.getSalary()));

        System.out.println("\n---- Salary Above 50000 (High to Low) ----");
        // Filter by salary >= 50000 and sort by salary in descending order
        List<Employee> highEarners = employees.stream()
                .filter(e -> e.getSalary() >= 50000)
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
        highEarners.forEach(e -> System.out.println(e.getName() + " -> " + e.getSalary()));

        System.out.println("\n---- Employee Names ----");
        // Extract all employee names into a list
        List<String> names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("\n---- Employees Grouped by Department ----");
        // Group employees by department
        Map<String, List<String>> employeesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())
                ));
        employeesByDept.forEach((dept, names_list) -> 
            System.out.println(dept + ": " + names_list)
        );

        System.out.println("\n---- Average Salary per Department ----");
        // Calculate average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
        avgSalaryByDept.forEach((dept, avgSalary) -> 
            System.out.printf("%s: %.2f%n", dept, avgSalary)
        );

        System.out.println("\n---- Summary Statistics ----");
        // Total salary paid
        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        System.out.printf("Total Salary Paid: %.2f ", totalSalary);

        // Number of CSE employees
        long cseCount = employees.stream()
                .filter(e -> "CSE".equalsIgnoreCase(e.getDepartment()))
                .count();
        System.out.printf("Number of CSE Employees: %d ", cseCount);

        // Highest paid employee
        employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .ifPresent(e -> System.out.printf("Highest Paid: %s (%.1f)%n", e.getName(), e.getSalary()));
    }

    private static List<Employee> getSampleEmployees() {
        return Arrays.asList(
            new Employee(101, "Rahul", "CSE", 55000.0),
            new Employee(102, "Sneha", "ECE", 62000.0),
            new Employee(103, "Kiran", "CSE", 48000.0),
            new Employee(104, "Divya", "MECH", 51000.0),
            new Employee(105, "Arjun", "ECE", 70000.0)
        );
    }
}
