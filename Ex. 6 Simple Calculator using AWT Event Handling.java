//Program:

//Ex. 6 Design and Develop a Simple Calculator using AWT Event Handling
//Enhanced Version with Improved Logic and Operation Display

import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends Frame implements ActionListener {
    TextField tfInput;
    TextArea taOutput;
    double num1 = 0, num2 = 0, result = 0;
    char op = '\0';
    boolean isOperationSet = false;
    boolean isNewNumber = true;

    // Button labels
    String[] btnLabels = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "C", "0", "=", "+"
    };
    Button[] btns = new Button[16];

    public SimpleCalculator() {
        setTitle("Simple Calculator");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Input field for display
        tfInput = new TextField();
        tfInput.setFont(new Font("Arial", Font.BOLD, 20));
        tfInput.setEditable(false);
        add(tfInput, BorderLayout.NORTH);

        // Output area to show operations
        taOutput = new TextArea();
        taOutput.setFont(new Font("Arial", Font.PLAIN, 12));
        taOutput.setEditable(false);
        taOutput.setRows(8);
        add(taOutput, BorderLayout.SOUTH);

        // Panel for buttons with Grid Layout
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        for (int i = 0; i < 16; i++) {
            btns[i] = new Button(btnLabels[i]);
            btns[i].setFont(new Font("Arial", Font.BOLD, 18));
            btns[i].addActionListener(this);
            panel.add(btns[i]);
        }

        add(panel, BorderLayout.CENTER);

        // Close window event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Initialize output
        updateOutput("Calculator Started", "", "", "");
    }

    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();

        // Handle number input (0-9 and decimal point)
        if ((str.charAt(0) >= '0' && str.charAt(0) <= '9') || str.equals(".")) {
            handleNumberInput(str);
        }
        // Handle Clear button
        else if (str.equals("C")) {
            handleClear();
        }
        // Handle Equals button
        else if (str.equals("=")) {
            handleEquals();
        }
        // Handle operators (+, -, *, /)
        else {
            handleOperator(str);
        }
    }

    private void handleNumberInput(String digit) {
        String currentText = tfInput.getText();

        // Prevent multiple decimal points
        if (digit.equals(".")) {
            if (currentText.contains(".")) {
                return;
            }
            if (currentText.isEmpty()) {
                currentText = "0";
            }
        }

        // If new number is expected, start fresh
        if (isNewNumber) {
            currentText = digit.equals(".") ? "0." : digit;
            isNewNumber = false;
        } else {
            currentText += digit;
        }

        tfInput.setText(currentText);
    }

    private void handleClear() {
        tfInput.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        op = '\0';
        isOperationSet = false;
        isNewNumber = true;
        taOutput.setText("Calculator Cleared\n");
    }

    private void handleOperator(String operator) {
        String currentText = tfInput.getText();

        // Validate that we have a number to work with
        if (currentText.isEmpty() && num1 == 0) {
            taOutput.append("Error: Enter a number first\n");
            return;
        }

        // If we already have an operation set, calculate the intermediate result
        if (isOperationSet && !currentText.isEmpty()) {
            num2 = Double.parseDouble(currentText);
            performCalculation();
            updateOutput(
                "First Number: " + num1,
                "Second Number: " + num2,
                "Click: " + getOperatorName(op),
                "Result: " + result
            );
            tfInput.setText(String.valueOf(result));
        } else if (!currentText.isEmpty()) {
            num1 = Double.parseDouble(currentText);
            updateOutput("First Number: " + num1, "", "", "");
        }

        op = operator.charAt(0);
        isOperationSet = true;
        isNewNumber = true;
        tfInput.setText("");
    }

    private void handleEquals() {
        String currentText = tfInput.getText();

        // Validate inputs
        if (currentText.isEmpty() || op == '\0') {
            taOutput.append("Error: Incomplete operation\n");
            return;
        }

        try {
            num2 = Double.parseDouble(currentText);
            performCalculation();
            updateOutput(
                "First Number: " + num1,
                "Second Number: " + num2,
                "Click: " + getOperatorName(op),
                "Result: " + result
            );
            tfInput.setText(String.valueOf(result));
            isOperationSet = false;
            isNewNumber = true;
            num1 = result; // Set result as num1 for chained operations
        } catch (NumberFormatException e) {
            taOutput.append("Error: Invalid number format\n");
        }
    }

    private void performCalculation() {
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    taOutput.append("Error: Cannot divide by zero\n");
                    tfInput.setText("Cannot divide by zero");
                    result = 0;
                    isOperationSet = false;
                    isNewNumber = true;
                } else {
                    result = num1 / num2;
                }
                break;
            default:
                taOutput.append("Error: Unknown operation\n");
        }
    }

    private String getOperatorName(char operator) {
        switch (operator) {
            case '+': return "Add";
            case '-': return "Subtract";
            case '*': return "Multiply";
            case '/': return "Divide";
            default: return "Unknown";
        }
    }

    private void updateOutput(String line1, String line2, String line3, String line4) {
        taOutput.setText(line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n");
    }

    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator();
        calc.setVisible(true);
    }
}
