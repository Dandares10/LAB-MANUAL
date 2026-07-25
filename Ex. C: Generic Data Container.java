
//Ex. C Design and implement a Generic Data Container using a Generic Class, Generic Method, and Bounded Type Parameters to store and process data of multiple types safely

//Program:

/**
 * Driver class to demonstrate container operation and compile-time boundaries.
 */
class Main {
    public static void main(String[] args) {
        // 1. Instantiating a container specifically for Double data
        DataContainer<Double> doubleContainer = new DataContainer<>();
        doubleContainer.addElement(10.5);
        doubleContainer.addElement(20.5);
        doubleContainer.addElement(30.0);

        System.out.println("Average of Doubles: " + doubleContainer.calculateAverage());

        // 2. Executing the independent generic method inside the container
        List<Integer> integerList = List.of(5, 22, 14, 9, 18);
        
        // The compiler automatically infers that type E is Integer
        Integer maxInteger = doubleContainer.processAndFindMax(integerList);
        System.out.println("Max Integer from list: " + maxInteger);

        // 3. Compile-Time Protection Demonstration
        // UNCOMMENTING THE LINE BELOW WILL CAUSE A COMPILE-TIME ERROR
        // DataContainer<String> stringContainer = new DataContainer<>(); 
        // Reason: String does not extend java.lang.Number
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * A type-safe generic container that restricts data elements to numeric types.
 * @param <T> The bounded type parameter restricted to subclasses of Number.
 */
public class DataContainer<T extends Number> {
    private final List<T> items;

    // Constructor initializing internal data storage
    public DataContainer() {
        this.items = new ArrayList<>();
    }

    // Class-scoped method to add data safely
    public void addElement(T item) {
        this.items.add(item);
    }

    // Class-scoped method to retrieve data by index
    public T getElement(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Invalid index provided.");
        }
        return this.items.get(index);
    }

    // Class-scoped method to compute the average value using Number bounds
    public double calculateAverage() {
        if (items.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (T item : items) {
            // Safe to invoke doubleValue() because T is bounded by Number
            sum += item.doubleValue(); 
        }
        return sum / items.size();
    }

    /**
     * An independent Generic Method with a multi-bounded type parameter.
     * Ensures structural inheritance (Number) and behavioral capability (Comparable).
     */
    public <E extends Number & Comparable<E>> E processAndFindMax(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        E max = list.get(0);
        for (E element : list) {
            // Safe to invoke compareTo because E implements Comparable
            if (element.compareTo(max) > 0) { 
                max = element;
            }
        }
        return max;
    }
}
