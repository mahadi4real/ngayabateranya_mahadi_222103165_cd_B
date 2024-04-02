package CardealershipSystem;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customers {

    private JFrame frame;
    private JTextField customer_idtxf;
    private JTextField nametxf;
    private JTextField phonetxf;
    private JTextField vehicletxf;

    private static final String DB_URL = "jdbc:mysql://localhost/cardealershipsystem_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Customers window = new Customers();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Customers() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(0, 64, 64));
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("CUSTOMERS");
        lblNewLabel.setBounds(223, 28, 200, 30);
        lblNewLabel.setForeground(new Color(255, 128, 64));
        lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 28));
        frame.getContentPane().add(lblNewLabel);

        JLabel customer_idlb = new JLabel("Customer_id");
        customer_idlb.setForeground(new Color(0, 0, 0));
        customer_idlb.setBounds(36, 116, 138, 30);
        customer_idlb.setFont(new Font("Montserrat", Font.PLAIN, 20));
        frame.getContentPane().add(customer_idlb);

        JLabel namelb = new JLabel("Name");
        namelb.setForeground(new Color(0, 0, 0));
        namelb.setBounds(49, 171, 99, 30);
        namelb.setFont(new Font("Montserrat", Font.PLAIN, 19));
        frame.getContentPane().add(namelb);

        JLabel phonelb = new JLabel("phone");
        phonelb.setForeground(new Color(0, 0, 0));
        phonelb.setBounds(49, 217, 99, 36);
        phonelb.setFont(new Font("Montserrat", Font.PLAIN, 19));
        frame.getContentPane().add(phonelb);

        JLabel vehiclelb = new JLabel("vehicle");
        vehiclelb.setForeground(new Color(0, 0, 0));
        vehiclelb.setFont(new Font("Montserrat", Font.PLAIN, 20));
        vehiclelb.setBounds(53, 275, 81, 25);
        frame.getContentPane().add(vehiclelb);

        customer_idtxf = new JTextField();
        customer_idtxf.setBounds(184, 116, 271, 30);
        frame.getContentPane().add(customer_idtxf);
        customer_idtxf.setColumns(10);

        nametxf = new JTextField();
        nametxf.setBounds(184, 171, 271, 33);
        frame.getContentPane().add(nametxf);
        nametxf.setColumns(10);

        phonetxf = new JTextField();
        phonetxf.setBounds(184, 223, 271, 30);
        frame.getContentPane().add(phonetxf);
        phonetxf.setColumns(10);

        vehicletxf = new JTextField();
        vehicletxf.setBounds(184, 276, 271, 30);
        frame.getContentPane().add(vehicletxf);
        vehicletxf.setColumns(10);

        JButton btninsert = new JButton("INSERT");
        btninsert.setFont(new Font("Montserrat", Font.PLAIN, 22));
        btninsert.setBounds(49, 373, 128, 30);
        frame.getContentPane().add(btninsert);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Montserrat", Font.BOLD, 24));
        btnUpdate.setBounds(360, 368, 129, 39);
        frame.getContentPane().add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Montserrat", Font.BOLD, 24));
        btnDelete.setBounds(513, 368, 119, 39);
        frame.getContentPane().add(btnDelete);

        JButton btnViewCustomers = new JButton("View");
        btnViewCustomers.setFont(new Font("Montserrat", Font.BOLD, 24));
        btnViewCustomers.setBounds(206, 368, 129, 39);
        frame.getContentPane().add(btnViewCustomers);

        frame.setBackground(new Color(128, 128, 64));
        frame.setBounds(100, 100, 670, 470);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btninsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });

        btnViewCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCustomers();
            }
        });
    }

    private void addCustomer() {
        String customer_id = customer_idtxf.getText().trim();
        String name = nametxf.getText().trim();
        String phone = phonetxf.getText().trim();
        String vehicle = vehicletxf.getText().trim();

        if (customer_id.isEmpty() || name.isEmpty() || phone.isEmpty() || vehicle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO customer (customer_id, name, phone, vehicle) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, Integer.parseInt(customer_id));
                statement.setString(2, name);
                statement.setString(3, phone);
                statement.setString(4, vehicle);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Customer added successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add customer");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding customer");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Customer ID must be an integer");
        
        }}

    private void updateCustomer() {
        String customer_id = customer_idtxf.getText().trim();
        String name = nametxf.getText().trim();
        String phone = phonetxf.getText().trim();
        String vehicle = vehicletxf.getText().trim();

        if (customer_id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer ID is required for updating");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE customer SET name=?, phone=?, vehicle=? WHERE customer_id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, phone);
                statement.setString(3, vehicle);
                statement.setInt(4, Integer.parseInt(customer_id));

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Customer updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update customer. Customer ID not found.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating customer");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Customer ID must be an integer");
        }
    }

    private void deleteCustomer() {
        String customer_id = customer_idtxf.getText().trim();

        if (customer_id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer ID is required for deletion");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM customer WHERE customer_id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, Integer.parseInt(customer_id));

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Customer deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete customer. Customer ID not found.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting customer");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Customer ID must be an integer");
        }
    }

    private void viewCustomers() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM customer";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("customer_id");
                model.addColumn("name");
                model.addColumn("phone");
                model.addColumn("vehicle");

                while (resultSet.next()) {
                    int customer_id = resultSet.getInt("customer_id");
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");
                    String vehicle = resultSet.getString("vehicle");

                    model.addRow(new Object[]{customer_id, name, phone, vehicle});
                }

                JTable dataTable = new JTable();
                JScrollPane scrollPane = new JScrollPane(dataTable);
                scrollPane.setBounds(500, 70, 700, 150);
                frame.getContentPane().add(scrollPane);

                dataTable.setModel(model);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error viewing customers");
        }
    }
}
