import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

class Student {
    int rollNo;
    String name;
    double percentage;

    public Student(int rollNo, String name, double percentage) {
        this.rollNo = rollNo;
        this.name = name;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return rollNo + " " + name + " " + percentage;
    }
}

public class Main {
    public static void main(String[] args) {
        // --- ArrayList Implementation ---
        System.out.println("Student Records (ArrayList)");
        System.out.println();
        System.out.println("Roll Name Percentage");
        
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(101, "Rahul", 88.5));
        studentList.add(new Student(102, "Sneha", 91.2));
        studentList.add(new Student(103, "Kiran", 84.8));

        for (Student s : studentList) {
            System.out.println(s.rollNo + " " + s.name + " " + s.percentage);
        }

        // --- Searching Operation ---
        int searchRoll = 102;
        System.out.println();
        System.out.println("Searching for Roll No: " + searchRoll);
        
        for (Student s : studentList) {
            if (s.rollNo == searchRoll) {
                System.out.println("Record Found");
                System.out.println(s.name);
                System.out.println("Roll No: " + s.rollNo);
                System.out.println("Name");
                System.out.println("Percentage: " + s.percentage);
                break;
            }
        }

        // --- Removal Operation ---
        System.out.println();
        System.out.println("After Removing First Student");
        System.out.println("Roll Name Percentage");
        
        if (!studentList.isEmpty()) {
            studentList.remove(0);
        }
        
        for (Student s : studentList) {
            System.out.println(s.rollNo + " " + s.name + " " + s.percentage);
        }

        // --- HashMap Implementation ---
        System.out.println();
        System.out.println("Student Records (HashMap)");
        
        // LinkedHashMap keeps elements in insertion order (101 -> 102 -> 103)
        Map<Integer, Student> studentMap = new LinkedHashMap<>();
        studentMap.put(101, new Student(101, "Rahul", 88.5));
        studentMap.put(102, new Student(102, "Sneha", 91.2));
        studentMap.put(103, new Student(103, "Kiran", 84.8));

        for (Map.Entry<Integer, Student> entry : studentMap.entrySet()) {
            Student s = entry.getValue();
            System.out.println(entry.getKey() + " " + s.name + " (" + s.percentage + "%)");
        }
    }
}
