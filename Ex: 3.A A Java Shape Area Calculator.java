
//Program:

//Ex: 3.A A Java Shape Area Calculator demonstrating packages, interfaces, and access specifiers.


// Importing specific classes from the shapes package
import shapes.Shape;
import shapes.Circle;
import shapes.Rectangle;

public class Main {
    public static void main(String[] args) {
        // Polymorphism: Using interface type for object references
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);

        System.out.println("--- Shape Area Calculator ---");
        
        // Executing methods via the interface
        circle.displayDetails();
        rectangle.displayDetails();
    }
}

package shapes;

// Public interface: accessible from any package
public interface Shape {
    // Interface methods are implicitly public and abstract
    double calculateArea();
    void displayDetails();
}

package shapes;

public class Circle implements Shape {
    // Private: Hidden from outside classes (Encapsulation)
    private double radius;

    // Public constructor
    public Circle(double radius) {
        this.radius = radius;
    }

    // Implementing interface method
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void displayDetails() {
        System.out.printf("Circle - Radius: %.2f | Area: %.2f%n", radius, calculateArea());
    }
}

package shapes;

public class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public void displayDetails() {
        System.out.printf("Rectangle - Width: %.2f, Height: %.2f | Area: %.2f%n", width, height, calculateArea());
    }
}
