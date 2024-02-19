package CardealershipSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class sale {

	private JFrame frame;
	private JTextField txtCustomer_id;
	private JTextField txtVehicle_id;
	private JTextField txtSaleDate;
	private JTextField txtTotalAmount;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnViewSales;
	
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
					sale window = new sale();
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
	public sale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 64, 64));
		frame.setBounds(100, 100, 686, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sale");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
		lblNewLabel.setBounds(294, 36, 102, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblCustomer_id = new JLabel("Customer_id");
		lblCustomer_id.setForeground(new Color(0, 0, 0));
		lblCustomer_id.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblCustomer_id.setBounds(58, 129, 145, 37);
		frame.getContentPane().add(lblCustomer_id);
		
		JLabel lblVehicle_id = new JLabel("Vehicle_id");
		lblVehicle_id.setForeground(new Color(0, 0, 0));
		lblVehicle_id.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblVehicle_id.setBounds(58, 189, 118, 27);
		frame.getContentPane().add(lblVehicle_id);
		
		JLabel lblSaleDate = new JLabel("SaleDate");
		lblSaleDate.setForeground(new Color(0, 0, 0));
		lblSaleDate.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblSaleDate.setBounds(58, 242, 102, 27);
		frame.getContentPane().add(lblSaleDate);
		
		JLabel lblTotalAmount = new JLabel("TotalAmount");
		lblTotalAmount.setForeground(new Color(0, 0, 0));
		lblTotalAmount.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblTotalAmount.setBounds(58, 296, 145, 27);
		frame.getContentPane().add(lblTotalAmount);
		
		txtCustomer_id = new JTextField();
		txtCustomer_id.setBounds(300, 133, 218, 37);
		frame.getContentPane().add(txtCustomer_id);
		txtCustomer_id.setColumns(10);
		
		txtVehicle_id = new JTextField();
		txtVehicle_id.setColumns(10);
		txtVehicle_id.setBounds(300, 188, 218, 37);
		frame.getContentPane().add(txtVehicle_id);
		
		txtSaleDate = new JTextField();
		txtSaleDate.setColumns(10);
		txtSaleDate.setBounds(300, 241, 218, 37);
		frame.getContentPane().add(txtSaleDate);
		
		txtTotalAmount = new JTextField();
		txtTotalAmount.setColumns(10);
		txtTotalAmount.setBounds(300, 295, 218, 37);
		frame.getContentPane().add(txtTotalAmount);
		
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnAdd.setBounds(75, 372, 89, 39);
		frame.getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnDelete.setBounds(518, 372, 119, 39);
		frame.getContentPane().add(btnDelete);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnUpdate.setBounds(347, 372, 129, 39);
		frame.getContentPane().add(btnUpdate);
		
		btnViewSales = new JButton("View");
		btnViewSales.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnViewSales.setBounds(190, 372, 129, 39);
		frame.getContentPane().add(btnViewSales);
		
		btnAdd.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        addSale();
		    }
		});

		btnUpdate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        updateSale();
		    }
		});

		btnViewSales.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        viewSales();
		    }
		});

		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        deleteSale();
		    }
		});

	}

		private void addSale() {
		    String customerID = txtCustomer_id.getText().trim();
		    String vehicleID = txtVehicle_id.getText().trim();
		    String saleDate = txtSaleDate.getText().trim();
		    String totalAmount = txtTotalAmount.getText().trim();

		    if (customerID.isEmpty() || vehicleID.isEmpty() || saleDate.isEmpty() || totalAmount.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "All fields are required");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "INSERT INTO sale (customer_id, vehicle_id, SaleDate, TotalAmount) VALUES (?, ?, ?, ?)";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, customerID);
		            statement.setString(2, vehicleID);
		            statement.setString(3, saleDate);
		            statement.setString(4, totalAmount);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Sale added successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to add sale");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error adding sale");
		    }
		}

		private void updateSale() {
		    String customerID = txtCustomer_id.getText().trim();
		    String vehicleID = txtVehicle_id.getText().trim();
		    String saleDate = txtSaleDate.getText().trim();
		    String totalAmount = txtTotalAmount.getText().trim();

		    if (customerID.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Customer ID is required for updating");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "UPDATE sale SET vehicle_id=?, SaleDate=?, TotalAmount=? WHERE customer_id=?";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, vehicleID);
		            statement.setString(2, saleDate);
		            statement.setString(3, totalAmount);
		            statement.setString(4, customerID);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Sale updated successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to update sale. Customer ID not found.");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error updating sale");
		    }
		}

		private void viewSales() {
		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "SELECT * FROM sale";
		        try (PreparedStatement statement = connection.prepareStatement(sql);
		             ResultSet resultSet = statement.executeQuery()) {

		            DefaultTableModel model = new DefaultTableModel();
		            model.addColumn("Customer ID");
		            model.addColumn("Vehicle ID");
		            model.addColumn("Sale Date");
		            model.addColumn("Total Amount");

		            while (resultSet.next()) {
		                String customerID = resultSet.getString("customer_id");
		                String vehicleID = resultSet.getString("vehicle_id");
		                String saleDate = resultSet.getString("SaleDate");
		                String totalAmount = resultSet.getString("TotalAmount");

		                model.addRow(new Object[]{customerID, vehicleID, saleDate, totalAmount});
		            }

		            JTable dataTable = new JTable();
		            JScrollPane scrollPane = new JScrollPane(dataTable);
		            scrollPane.setBounds(550, 70, 700, 150);
		            frame.getContentPane().add(scrollPane);

		            dataTable.setModel(model);

		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error viewing sales");
		    }
		}

		private void deleteSale() {
		    String customerID = txtCustomer_id.getText().trim();

		    if (customerID.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Customer ID is required for deletion");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "DELETE FROM sale WHERE customer_id=?";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, customerID);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Sale deleted successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to delete sale. Customer ID not found.");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error deleting sale");
		    }
		}
	}
