
//Program:

//Ex. 3.B Design a class that computes and stores the value of Pi using public, private, and protected access specifiers to control member access

// Base class managing the mathematical calculation
abstract class PiCalculatorBase {
    // Accessible by derived classes, hidden from unrelated classes
    protected double calculatedPi;

    // Uses the Leibniz formula to estimate Pi
    protected void computeLeibniz(int iterations) {
        double sum = 0.0;
        for (int i = 0; i < iterations; ++i) {
            double term = 1.0 / (2 * i + 1);
            if (i % 2 == 0) {
                sum += term;
            } else {
                sum -= term;
            }
        }
        this.calculatedPi = sum * 4.0;
    }
}

// Derived class managing storage and user interaction
public class PiManager extends PiCalculatorBase {
    // Completely hidden; accessible only within this class
    private int precisionIterations;

    // Private helper method for internal data validation
    private boolean isValidIterationCount(int iterations) {
        return iterations > 0;
    }

    // Public constructor
    public PiManager(int iterations) {
        if (isValidIterationCount(iterations)) {
            this.precisionIterations = iterations;
        } else {
            this.precisionIterations = 1000; // Default fallback
        }
        this.calculatedPi = 0.0;
    }

    // Public interface to trigger calculation
    public void runCalculation() {
        // Inherited protected method
        computeLeibniz(precisionIterations); 
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

    // Main method to run the application
    public static void main(String[] args) {
        // Instantiate the manager with 1,000,000 iterations
        PiManager piObj = new PiManager(1000000);
        
        // Compute and retrieve the value
        piObj.runCalculation();
        
        System.out.println("Iterations used: " + piObj.getIterations());
        System.out.println("Computed Value of Pi: " + piObj.getPi());
        System.out.println("Standard Math.PI reference: " + Math.PI);
    }
}

