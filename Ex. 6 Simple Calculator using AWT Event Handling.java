
//Program:

//Ex. 6 Design and Develop an Simple Calculator using AWT Event Handling

import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends Frame implements ActionListener {
    TextField tfInput;
    double num1 = 0, num2 = 0, result = 0;
    char op;

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
        setSize(300, 400);
        setLayout(new BorderLayout());

        // Text field for display
        tfInput = new TextField();
        tfInput.setFont(new Font("Arial", Font.BOLD, 20));
        tfInput.setEditable(false);
        add(tfInput, BorderLayout.NORTH);

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
    }

    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();

        if (str.charAt(0) >= '0' && str.charAt(0) <= '9' || str.equals(".")) {
            tfInput.setText(tfInput.getText() + str);
        } else if (str.equals("C")) {
            tfInput.setText("");
            num1 = num2 = result = 0;
        } else if (str.equals("=")) {
            num2 = Double.parseDouble(tfInput.getText());
            switch (op) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/': 
                    if (num2 != 0) result = num1 / num2;
                    else { tfInput.setText("Error"); return; }
                    break;
            }
            tfInput.setText(String.valueOf(result));
        } else {
            if (!tfInput.getText().equals("")) {
                num1 = Double.parseDouble(tfInput.getText());
                op = str.charAt(0);
                tfInput.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator();
        calc.setVisible(true);
    }
}
