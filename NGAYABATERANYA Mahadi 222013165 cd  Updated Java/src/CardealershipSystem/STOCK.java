package CardealershipSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
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

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class STOCK {

	private JFrame frame;
	private JTextField txtStock_id;
	private JTextField txtVehicle_id;
	private JTextField txtQnty_in;
	private JTextField txtQnty_out;


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
					STOCK window = new STOCK();
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
	public STOCK() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 64, 64));
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STOCK");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 40));
		lblNewLabel.setBounds(265, 29, 186, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblStock_id = new JLabel("stock_id");
		lblStock_id.setForeground(new Color(0, 0, 0));
		lblStock_id.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblStock_id.setBounds(31, 107, 142, 30);
		frame.getContentPane().add(lblStock_id);
		
		JLabel lblVehicle_id = new JLabel("vehicle_id");
		lblVehicle_id.setForeground(new Color(0, 0, 0));
		lblVehicle_id.setFont(new Font("Montserrat", lblVehicle_id.getFont().getStyle(), 20));
		lblVehicle_id.setBounds(31, 169, 111, 22);
		frame.getContentPane().add(lblVehicle_id);
		
		JLabel lblQnty_in = new JLabel("quantity_in");
		lblQnty_in.setForeground(new Color(0, 0, 0));
		lblQnty_in.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblQnty_in.setBounds(31, 220, 142, 30);
		frame.getContentPane().add(lblQnty_in);
		
		JLabel lblQnty_out = new JLabel("quantity_out");
		lblQnty_out.setForeground(new Color(0, 0, 0));
		lblQnty_out.setFont(new Font("Montserrat", Font.PLAIN, 20));
		lblQnty_out.setBounds(26, 273, 163, 30);
		frame.getContentPane().add(lblQnty_out);
		
		txtStock_id = new JTextField();
		txtStock_id.setBounds(199, 111, 351, 30);
		frame.getContentPane().add(txtStock_id);
		txtStock_id.setColumns(10);
		
		txtVehicle_id = new JTextField();
		txtVehicle_id.setBounds(199, 169, 351, 30);
		frame.getContentPane().add(txtVehicle_id);
		txtVehicle_id.setColumns(10);
		
		txtQnty_in = new JTextField();
		txtQnty_in.setBounds(199, 224, 351, 30);
		frame.getContentPane().add(txtQnty_in);
		txtQnty_in.setColumns(10);
		
		txtQnty_out = new JTextField();
		txtQnty_out.setBounds(199, 277, 351, 30);
		frame.getContentPane().add(txtQnty_out);
		txtQnty_out.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Montserrat", Font.BOLD, 20));
		btnAdd.setBounds(46, 371, 89, 40);
		frame.getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setFont(new Font("Montserrat", Font.BOLD, 20));
		btnUpdate.setBounds(173, 371, 134, 40);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnDelete.setBounds(355, 371, 119, 39);
		frame.getContentPane().add(btnDelete);
		
		JButton btnViewStock = new JButton("View");
		btnViewStock.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnViewStock.setBounds(503, 371, 129, 39);
		frame.getContentPane().add(btnViewStock);
		frame.setBounds(100, 100, 703, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnAdd.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        addStock();
		    }
		});

		btnUpdate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        updateStock();
		    }
		});

		btnViewStock.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        viewStock();
		    }
		});

		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        deleteStock();
		    }
		});

	}

		private void addStock() {
		    String vehicleId = txtVehicle_id.getText().trim();
		    String quantityIn = txtQnty_in.getText().trim();
		    String quantityOut = txtQnty_out.getText().trim();

		    if (vehicleId.isEmpty() || quantityIn.isEmpty() || quantityOut.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "All fields are required");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "INSERT INTO stock (vehicle_id, quantity_in, quantity_out) VALUES (?, ?, ?)";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, vehicleId);
		            statement.setString(2, quantityIn);
		            statement.setString(3, quantityOut);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Stock added successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to add stock");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error adding stock");
		    }
		}

		private void updateStock() {
		    String vehicleId = txtVehicle_id.getText().trim();
		    String quantityIn = txtQnty_in.getText().trim();
		    String quantityOut = txtQnty_out.getText().trim();

		    if (vehicleId.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Vehicle ID is required for updating");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "UPDATE stock SET quantity_in=?, quantity_out=? WHERE vehicle_id=?";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, quantityIn);
		            statement.setString(2, quantityOut);
		            statement.setString(3, vehicleId);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Stock updated successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to update stock. Vehicle ID not found.");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error updating stock");
		    }
		}

		private void viewStock() {
		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "SELECT * FROM stock";
		        try (PreparedStatement statement = connection.prepareStatement(sql);
		             ResultSet resultSet = statement.executeQuery()) {

		            DefaultTableModel model = new DefaultTableModel();
		            model.addColumn("Stock ID");
		            model.addColumn("Vehicle ID");
		            model.addColumn("Quantity In");
		            model.addColumn("Quantity Out");

		            while (resultSet.next()) {
		                int stockId = resultSet.getInt("stock_id");
		                String vehicleId = resultSet.getString("vehicle_id");
		                String quantityIn = resultSet.getString("quantity_in");
		                String quantityOut = resultSet.getString("quantity_out");

		                model.addRow(new Object[]{stockId, vehicleId, quantityIn, quantityOut});
		            }

		            JTable dataTable = new JTable();
		            JScrollPane scrollPane = new JScrollPane(dataTable);
		            scrollPane.setBounds(550, 70, 700, 150);
		            frame.getContentPane().add(scrollPane);

		            dataTable.setModel(model);

		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error viewing stock");
		    }
		}

		private void deleteStock() {
		    String vehicleId = txtVehicle_id.getText().trim();

		    if (vehicleId.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Vehicle ID is required for deletion");
		        return;
		    }

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
		        String sql = "DELETE FROM stock WHERE vehicle_id=?";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, vehicleId);

		            int rowsAffected = statement.executeUpdate();

		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Stock deleted successfully");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to delete stock. Vehicle ID not found.");
		            }
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error deleting stock");
		    }
		}
}