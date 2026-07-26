
//Program:

//Ex. 8 Create a Student Registration Form with fields like name, roll number, and course using Java Swing components (Java GUI Practice - Student Registration Form).

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRegistrationForm extends JFrame implements ActionListener {
    
    // Declarations of Swing components
    private JLabel titleLabel, nameLabel, rollLabel, courseLabel;
    private JTextField nameTextField, rollTextField;
    private JComboBox<String> courseComboBox;
    private JButton submitButton, clearButton;

    public StudentRegistrationForm() {
        // Set up the layout frame parameters
        setTitle("Student Registration Form");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the frame on screen
        setLayout(null); // Absolute layout positioning for precision

        // Header Title
        titleLabel = new JLabel("Student Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(80, 20, 250, 30);
        add(titleLabel);

        // Name Label and TextField
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(40, 80, 100, 25);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(150, 80, 180, 25);
        add(nameTextField);

        // Roll Number Label and TextField
        rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(40, 130, 100, 25);
        add(rollLabel);

        rollTextField = new JTextField();
        rollTextField.setBounds(150, 130, 180, 25);
        add(rollTextField);

        // Course Label and Dropdown ComboBox
        courseLabel = new JLabel("Skill:");
        courseLabel.setBounds(40, 180, 100, 25);
        add(courseLabel);

        String[] courses = {"Computer Science", "Information Technology", "Mechanical Engineering", "Electrical Engineering", "Business Administration"};
        courseComboBox = new JComboBox<>(courses);
        courseComboBox.setBounds(150, 180, 180, 25);
        add(courseComboBox);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(60, 240, 100, 30);
        submitButton.addActionListener(this);
        add(submitButton);

        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.setBounds(210, 240, 100, 30);
        clearButton.addActionListener(this);
        add(clearButton);

        // Set visibility
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText().trim();
            String roll = rollTextField.getText().trim();
            String course = (String) courseComboBox.getSelectedItem();

            // Simple data field verification
            if (name.isEmpty() || roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String message = "Registration Successful!\n\n" +
                                 "Name: " + name + "\n" +
                                 "Roll Number: " + roll + "\n" +
                                 "Skill: " + course;
                JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == clearButton) {
            // Reset text components and choices
            nameTextField.setText("");
            rollTextField.setText("");
            courseComboBox.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        // Enqueue GUI processing block thread safely
        SwingUtilities.invokeLater(() -> new StudentRegistrationForm());
    }
}
