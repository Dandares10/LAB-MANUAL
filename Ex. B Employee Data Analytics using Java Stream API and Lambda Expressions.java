
//Ex. B Design and implement an Employee Data Analytics program that uses Java Stream API and Lambda Expressions to filter, sort, group, and summarize employee data

//Program:

package analytics;

import java.util.List;

public record Employee(
    int id,
    String name,
    String department,
    String role,
    double salary,
    int age,
    List<String> skills
) {}

package analytics;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalytics {

    public static void main(String[] args) {
        List<Employee> employees = getSampleEmployees();

        System.out.println("=== 1. FILTERING: High-Earning Software Engineers ===");
        // Filters by role and salary threshold
        List<Employee> highEarners = employees.stream()
                .filter(e -> "Engineering".equalsIgnoreCase(e.getDepartment()))
                .filter(e -> "Software Engineer".equalsIgnoreCase(e.getRole()))
                .filter(e -> e.getSalary() >= 90000)
                .collect(Collectors.toList());
        highEarners.forEach(e -> System.out.printf(" - %s ($%.2f)%n", e.getName(), e.getSalary()));

        System.out.println("\n=== 2. SORTING: Employees by Salary (Desc), then Age (Asc) ===");
        // Uses chained comparators for deep sorting
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()
                        .thenComparingInt(Employee::getAge))
                .collect(Collectors.toList());
        sortedEmployees.forEach(e -> System.out.printf(" - %s: $%.2f, Age: %d%n", 
                e.getName(), e.getSalary(), e.getAge()));

        System.out.println("\n=== 3. GROUPING: Headcount and Employees by Department ===");
        // Groups employees entirely by department name
        Map<String, List<Employee>> employeesByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        employeesByDept.forEach((dept, deptList) -> {
            System.out.printf(" Department: %s (Count: %d)%n", dept, deptList.size());
            deptList.forEach(e -> System.out.println("   * " + e.getName()));
        });

        System.out.println("\n=== 4. SUMMARIZATION: Salary Statistics by Department ===");
        // Computes count, sum, min, average, and max simultaneously per department
        Map<String, DoubleSummaryStatistics> salaryStatsByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summarizingDouble(Employee::getSalary)
                ));
        salaryStatsByDept.forEach((dept, stats) -> {
            System.out.printf(" Department: %s%n", dept);
            System.out.printf("   * Average Salary : $%.2f%n", stats.getAverage());
            System.out.printf("   * Highest Salary : $%.2f%n", stats.getMax());
            System.out.printf("   * Total Budget   : $%.2f%n", stats.getSum());
        });

        System.out.println("\n=== 5. ADVANCED: Unique Skills Across the Company ===");
        // Uses flatMap to flatten individual skill lists into a single distinct stream
        List<String> uniqueSkills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(" Technical Footprint: " + uniqueSkills);

        System.out.println("\n=== 6. REDUCTION: Highest Paid Employee ===");
        // Uses reduce operation to find the max value safely
        employees.stream()
                .reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2)
                .ifPresent(e -> System.out.printf(" Top Earner: %s ($%.2f) in %s%n", 
                        e.getName(), e.getSalary(), e.getDepartment()));
    }

    private static List<Employee> getSampleEmployees() {
        return Arrays.asList(
            new Employee(1, "Alice Smith", "Engineering", "Software Engineer", 95000, 28, Arrays.asList("Java", "Spring", "Docker")),
            new Employee(2, "Bob Jones", "Engineering", "Software Engineer", 88000, 25, Arrays.asList("Java", "AWS", "Kubernetes")),
            new Employee(3, "Charlie Brown", "Engineering", "Tech Lead", 120000, 35, Arrays.asList("Java", "Architecture", "Cloud")),
            new Employee(4, "Diana Prince", "HR", "HR Manager", 75000, 32, Arrays.asList("Recruiting", "Communication")),
            new Employee(5, "Evan Wright", "Marketing", "Data Analyst", 71000, 29, Arrays.asList("Python", "SQL", "Tableau")),
            new Employee(6, "Fiona Gallagher", "Engineering", "Software Engineer", 95000, 24, Arrays.asList("Python", "Go", "Docker")),
            new Employee(7, "George Costanza", "Marketing", "Director", 110000, 41, Arrays.asList("Strategy", "SEO"))
        );
    }
}
