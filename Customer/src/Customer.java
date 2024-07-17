import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends JFrame implements ActionListener {
    private List<CustomerRecord> customers;
    private int currentIndex = 0;

    private JLabel idLabel, lastNameLabel, firstNameLabel, phoneLabel;
    private JTextField idField, lastNameField, firstNameField, phoneField;
    private JButton prevButton, nextButton;

    public Customer() {
        // Initialize customer data from database
        customers = fetchCustomersFromDatabase();

        // Set up the frame
        setTitle("Customer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Create labels and fields
        idLabel = new JLabel("ID:");
        lastNameLabel = new JLabel("Last Name:");
        firstNameLabel = new JLabel("First Name:");
        phoneLabel = new JLabel("Phone:");

        idField = new JTextField();
        lastNameField = new JTextField();
        firstNameField = new JTextField();
        phoneField = new JTextField();

        idField.setEditable(false);
        lastNameField.setEditable(false);
        firstNameField.setEditable(false);
        phoneField.setEditable(false);

        // Create buttons
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        prevButton.addActionListener(this);
        nextButton.addActionListener(this);

        // Add components to the frame
        add(idLabel);
        add(idField);
        add(lastNameLabel);
        add(lastNameField);
        add(firstNameLabel);
        add(firstNameField);
        add(phoneLabel);
        add(phoneField);
        add(prevButton);
        add(nextButton);

        // Display the first customer record
        displayCustomer(currentIndex);
    }

    private List<CustomerRecord> fetchCustomersFromDatabase() {
        List<CustomerRecord> customerList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");

            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String lastName = resultSet.getString("customer_last_name");
                String firstName = resultSet.getString("customer_first_name");
                String phone = resultSet.getString("customer_phone");
                customerList.add(new CustomerRecord(id, lastName, firstName, phone));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return customerList;
    }

    private void displayCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            CustomerRecord customer = customers.get(index);
            idField.setText(String.valueOf(customer.getId()));
            lastNameField.setText(customer.getLastName());
            firstNameField.setText(customer.getFirstName());
            phoneField.setText(customer.getPhone());
        }
        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < customers.size() - 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            if (currentIndex > 0) {
                currentIndex--;
                displayCustomer(currentIndex);
            }
        } else if (e.getSource() == nextButton) {
            if (currentIndex < customers.size() - 1) {
                currentIndex++;
                displayCustomer(currentIndex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Customer customerFrame = new Customer();
            customerFrame.setVisible(true);
        });
    }

    // Inner class to represent customer records
    private static class CustomerRecord {
        private int id;
        private String lastName;
        private String firstName;
        private String phone;

        public CustomerRecord(int id, String lastName, String firstName, String phone) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getPhone() {
            return phone;
        }
    }
}