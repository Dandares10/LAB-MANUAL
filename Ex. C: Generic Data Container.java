
//Ex. C Design and implement a Generic Data Container using a Generic Class, Generic Method, and Bounded Type Parameters to store and process data of multiple types safely

//Program:

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Driver class to demonstrate container operation and compile-time boundaries.
 */
class Main {
    public static void main(String[] args) {
        System.out.println("---- Generic Box Demo ----\n");
        
        // 1. Demonstrate generic box with Integer
        GenericBox<Integer> intBox = new GenericBox<>(100);
        System.out.println("Integer Box Value: " + intBox.getValue());
        System.out.println("Type of stored item: " + intBox.getType());
        
        // 2. Demonstrate generic box with String
        GenericBox<String> stringBox = new GenericBox<>("Hello Generics");
        System.out.println("String Box Value: " + stringBox.getValue());
        System.out.println("Type of stored item: " + stringBox.getType());
        
        System.out.println("\n---- Key-Value Pairs ----\n");
        
        // 3. Demonstrate generic key-value container
        KeyValueContainer<String, Integer> studentGrades = new KeyValueContainer<>();
        studentGrades.put("Rahul", 88);
        studentGrades.put("101", 101);  // Key as string, value as integer (generic)
        studentGrades.displayAll();
        
        System.out.println();
        
        // 4. Demonstrate DataContainer with bounded type parameters
        DataContainer<Integer> numberContainer = new DataContainer<>();
        numberContainer.addElement(75);
        numberContainer.addElement(89);
        numberContainer.addElement(92);
        
        Integer maxNumber = numberContainer.findMax();
        System.out.println("Maximum Number: " + maxNumber);
        
        // 5. Generic method with Comparable bound for String
        List<String> names = List.of("Rahul", "Sneha", "Amit");
        String maxName = numberContainer.processAndFindMax(names);
        System.out.println("Maximum (Alphabetical): " + maxName);
        
        // 6. Generic method with Number and Comparable bounds for Double
        List<Double> marks = List.of(85.5, 92.3, 88.7);
        Double maxMarks = numberContainer.processAndFindMax(marks);
        System.out.println("Maximum Marks: " + maxMarks);
    }
}

/**
 * A simple generic box to store and retrieve any type of data.
 * @param <T> The type parameter for the box
 */
class GenericBox<T> {
    private T value;
    
    public GenericBox(T value) {
        this.value = value;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public String getType() {
        return value.getClass().getName();
    }
}

/**
 * A generic key-value container that stores pairs of any types.
 * @param <K> The type parameter for keys
 * @param <V> The type parameter for values
 */
class KeyValueContainer<K, V> {
    private Map<K, V> map;
    
    public KeyValueContainer() {
        this.map = new HashMap<>();
    }
    
    public void put(K key, V value) {
        this.map.put(key, value);
    }
    
    public V get(K key) {
        return this.map.get(key);
    }
    
    public void displayAll() {
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

/**
 * A type-safe generic container that restricts data elements to numeric types.
 * @param <T> The bounded type parameter restricted to subclasses of Number.
 */
class DataContainer<T extends Number> {
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
    
    // Method to find maximum number in the container
    public T findMax() {
        if (items.isEmpty()) {
            return null;
        }
        T max = items.get(0);
        for (T item : items) {
            if (item.doubleValue() > max.doubleValue()) {
                max = item;
            }
        }
        return max;
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
    
    /**
     * Overloaded generic method to find max from Comparable items (non-numeric).
     */
    public <E extends Comparable<E>> E processAndFindMax(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        E max = list.get(0);
        for (E element : list) {
            if (element.compareTo(max) > 0) { 
                max = element;
            }
        }
        return max;
    }
}
