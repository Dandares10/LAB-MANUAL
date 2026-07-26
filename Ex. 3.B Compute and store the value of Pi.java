
import java.util.Scanner;

//Program:

//Ex. 3.B Design a class that computes and stores the value of Pi using public, private, and protected access specifiers to control member access

// Base class managing the mathematical calculation
abstract class PiCalculatorBase {
    // Accessible by derived classes, hidden from unrelated classes
    protected double calculatedPi;
    protected int termsUsed;

    // Uses the Leibniz formula to estimate Pi
    protected void computeLeibniz(int iterations) {
        double sum = 0.0;
        for (int i = 0; i < iterations; ++i) {
            double term = 4.0 / (2 * i + 1);
            if (i % 2 == 0) {
                sum += term;
            } else {
                sum -= term;
            }
        }
        this.calculatedPi = sum;
        this.termsUsed = iterations;
    }

    // Protected method to display precision info
    protected void displayPrecisionInfo() {
        System.out.println("\nProtected Method - Displaying Precision Info: Precision used: " + termsUsed + " terms");
        System.out.println("Series used: Leibniz Series (4/1 - 4/3+4/5 - 4/7+4/9..)");
    }
}

// Derived class managing storage and user interaction
public class PiManager extends PiCalculatorBase {
    // Completely hidden; accessible only within this class
    private int precisionIterations;
    private double rawComputedValue;

    // Private helper method for internal data validation
    private boolean isValidIterationCount(int iterations) {
        return iterations > 0;
    }

    // Private method to display private data
    private void displayPrivateData() {
        System.out.println("\nPrivate Data - Accessed only within class: Raw computed value (private): " + this.rawComputedValue);
    }

    // Public constructor
    public PiManager(int iterations) {
        if (isValidIterationCount(iterations)) {
            this.precisionIterations = iterations;
        } else {
            this.precisionIterations = 1000; // Default fallback
        }
        this.calculatedPi = 0.0;
        this.rawComputedValue = 0.0;
    }

    // Public interface to trigger calculation
    public void runCalculation() {
        // Inherited protected method
        computeLeibniz(precisionIterations);
        this.rawComputedValue = this.calculatedPi;
    }

    // Public getter to retrieve the stored value
    public double getPi() {
        // Inherited protected variable
        return this.calculatedPi; 
    }

    // Public getter to check configured iterations
    public int getIterations() {
        return this.precisionIterations;
    }

    // Public method to display result
    public void displayPublicResult() {
        System.out.println("Public Method - Displaying Result: Approximated value of Pi: " + this.calculatedPi);
    }

    // Main method to run the application
    public static void main(String[] args) {
        System.out.println("== Pi Calculator using Access Specifiers\n");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of terms for Pi approximation: ");
        int terms = scanner.nextInt();
        
        System.out.println("\nCalculating Pi using Leibniz Series...");
        
        // Instantiate the manager with user-provided iterations
        PiManager piObj = new PiManager(terms);
        
        // Compute and retrieve the value
        piObj.runCalculation();
        
        // Display results using different access specifiers
        piObj.displayPublicResult();
        piObj.displayPrecisionInfo();
        piObj.displayPrivateData();
        
        scanner.close();
    }
}

