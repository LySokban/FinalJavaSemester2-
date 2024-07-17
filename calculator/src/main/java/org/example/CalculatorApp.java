import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame {
    private JTextField firstNumberField, secondNumberField, resultField;
    private double num1, num2, result;
    private String operator;

    public CalculatorApp() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 400);

        // Labels for First Number, Second Number, and Result
        JLabel firstLabel = new JLabel("First Number:");
        JLabel secondLabel = new JLabel("Second Number:");
        JLabel resultLabel = new JLabel("Result:");

        // First Number Field Panel
        JPanel firstPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        firstPanel.add(firstLabel);
        firstNumberField = createTextField();
        firstPanel.add(firstNumberField);

        // Second Number Field Panel
        JPanel secondPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        secondPanel.add(secondLabel);
        secondNumberField = createTextField();
        secondPanel.add(secondNumberField);

        // Result Field Panel
        JPanel resultPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        resultPanel.add(resultLabel);
        resultField = createTextField();
        resultField.setEditable(false);
        resultPanel.add(resultField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 5, 5)); // 4 rows, 2 columns, gaps
        addButtons(buttonPanel);

        // Layout Components
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inputPanel.add(firstPanel);
        inputPanel.add(secondPanel);
        inputPanel.add(resultPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(10); // Start with a reasonable initial size
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setPreferredSize(new Dimension(50, 50)); // Set a preferred size for alignment
        return textField;
    }

    private void addButtons(JPanel panel) {
        String[] buttonLabels = {"+", "-", "*", "/", "%", "Clear", "="};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/") || command.equals("%")) {
                operator = command;
                num1 = Double.parseDouble(firstNumberField.getText());
            } else if (command.equals("Clear")) {
                firstNumberField.setText("");
                secondNumberField.setText("");
                resultField.setText("");
            } else if (command.equals("=")) {
                if (operator != null && !secondNumberField.getText().isEmpty()) {
                    num2 = Double.parseDouble(secondNumberField.getText());
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                resultField.setText("Error: Division by zero");
                                return;
                            }
                            break;
                        case "%":
                            if (num2 != 0) {
                                result = num1 % num2;
                            } else {
                                resultField.setText("Error: Modulus by zero");
                                return;
                            }
                            break;
                    }
                    resultField.setText(String.valueOf(result));
                }
            }
        }
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}
