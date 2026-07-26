
//Program:

//Ex: 3.A A Java Shape Area Calculator demonstrating packages, interfaces, and access specifiers.


// Importing specific classes from the shapes package
import shapes.Shape;
import shapes.Circle;
import shapes.Rectangle;
import shapes.Triangle;

public class Main {
    public static void main(String[] args) {
        // Polymorphism: Using interface type for object references
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 8.0);

        System.out.println("--- Shape Area Calculator ---");
        System.out.println();
        
        // Executing methods via the interface
        circle.displayDetails();
        rectangle.displayDetails();
        triangle.displayDetails();
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
        System.out.printf("Circle:%nRadius = %.1f %nArea of Circle = %.14f%n", radius, calculateArea());
        System.out.println();
    }
}

package shapes;

public class Rectangle implements Shape {
    private double width;
    private double length;

    public Rectangle(double length, double width) {
        this.width = width;
        this.length = length;
    }

    @Override
    public double calculateArea() {
        return width * length;
    }

    @Override
    public void displayDetails() {
        System.out.printf("Rectangle:%nLength = %.1f, Width = %.1f%nArea of Rectangle = %.1f%n", length, width, calculateArea());
        System.out.println();
    }
}

package shapes;

public class Triangle implements Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5*base * height;
    }

    @Override
    public void displayDetails() {
        System.out.printf("Triangle:%nBase = %.1f, Height = %.1f%nArea of Triangle = %.1f%n", base, height, calculateArea());
        System.out.println();
    }
}
