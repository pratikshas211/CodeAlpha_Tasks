import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class StudentGradeTracker {
    private JFrame frame;
    private JTextField gradeInput;
    private JTextArea resultArea;
    private ArrayList<Integer> grades;

    public StudentGradeTracker() {
        grades = new ArrayList<>();
        frame = new JFrame("Student Grade Tracker");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Student Grade:");
        frame.add(label);

        gradeInput = new JTextField(10);
        frame.add(gradeInput);

        JButton addButton = new JButton("Add Grade");
        frame.add(addButton);
        addButton.addActionListener((ActionEvent e) -> {
            try {
                int grade = Integer.parseInt(gradeInput.getText());
                grades.add(grade);
                gradeInput.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton calculateButton = new JButton("Calculate");
        frame.add(calculateButton);
        calculateButton.addActionListener((ActionEvent e) -> {
            if (grades.isEmpty()) {
                resultArea.setText("No grades entered.");
            } else {
                int highest = findHighest(grades);
                int lowest = findLowest(grades);
                double average = calculateAverage(grades);
                
                resultArea.setText("Highest Grade: " + highest + "\n" +
                        "Lowest Grade: " + lowest + "\n" +
                                "Average Grade: " + average);
            }
        });

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }

    public int findHighest(ArrayList<Integer> grades) {
        int max = grades.get(0);
        for (int grade : grades) {
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public int findLowest(ArrayList<Integer> grades) {
        int min = grades.get(0);
        for (int grade : grades) {
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    public double calculateAverage(ArrayList<Integer> grades) {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::new);
    }
}
