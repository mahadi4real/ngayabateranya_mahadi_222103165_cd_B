package CardealershipSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class vehicles {

	private JFrame frame;
	private JTextField txtMake;
	private JTextField txtColor;
	private JTextField txtPrice;
	private JTextField txtSeats;

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
					vehicles window = new vehicles();
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
	public vehicles() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 64, 64));
		frame.setBounds(100, 100, 764, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("VEHICLES");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 31));
		lblNewLabel.setBounds(266, 22, 176, 56);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblMake = new JLabel("Make");
		lblMake.setForeground(new Color(0, 0, 0));
		lblMake.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblMake.setBounds(70, 139, 88, 28);
		frame.getContentPane().add(lblMake);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setForeground(new Color(0, 0, 0));
		lblColor.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblColor.setBounds(70, 192, 88, 28);
		frame.getContentPane().add(lblColor);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setForeground(new Color(0, 0, 0));
		lblPrice.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblPrice.setBounds(70, 246, 71, 28);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblSeats = new JLabel("Seats");
		lblSeats.setForeground(new Color(0, 0, 0));
		lblSeats.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblSeats.setBounds(70, 296, 128, 28);
		frame.getContentPane().add(lblSeats);
		
		txtMake = new JTextField();
		txtMake.setBounds(328, 139, 222, 28);
		frame.getContentPane().add(txtMake);
		txtMake.setColumns(10);
		
		txtColor = new JTextField();
		txtColor.setBounds(328, 192, 222, 28);
		frame.getContentPane().add(txtColor);
		txtColor.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(328, 246, 222, 28);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		
		txtSeats = new JTextField();
		txtSeats.setBounds(328, 296, 222, 28);
		frame.getContentPane().add(txtSeats);
		txtSeats.setColumns(10);
		
		JButton btnInsert = new JButton("INSERT");
		btnInsert.setFont(new Font("Montserrat", Font.BOLD, 22));
		btnInsert.setBounds(36, 389, 122, 37);
		frame.getContentPane().add(btnInsert);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setFont(new Font("Montserrat", Font.BOLD, 22));
		btnUpdate.setBounds(389, 389, 141, 37);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnViewVehicles = new JButton("View");
		btnViewVehicles.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnViewVehicles.setBounds(209, 387, 129, 39);
		frame.getContentPane().add(btnViewVehicles);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Montserrat", Font.BOLD, 22));
		btnDelete.setBounds(578, 389, 122, 37);
		frame.getContentPane().add(btnDelete);
		
		btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addVehicle();
            }
        });
		
		btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               updateVehicle();
            }
        });


        btnViewVehicles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewVehicles();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteVehicle();
            }
        });
	}
		
        private void addVehicle() {
            String make = txtMake.getText().trim();
            String color = txtColor.getText().trim();
            String price = txtPrice.getText().trim();
            String seats = txtSeats.getText().trim();

            if (make.isEmpty() || color.isEmpty() || price.isEmpty() || seats.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required");
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO vehicles (make, color, price, seats) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, make);
                    statement.setString(2, color);
                    statement.setInt(3, Integer.parseInt(price));
                    statement.setInt(4, Integer.parseInt(seats));

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle added successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add vehicle");
                    }
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding vehicle");
            }
        }

        private void updateVehicle() {
            String make = txtMake.getText().trim();
            String color = txtColor.getText().trim();
            String price = txtPrice.getText().trim();
            String seats = txtSeats.getText().trim();

            if (make.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Make is required for updating");
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "UPDATE vehicles SET color=?, price=?, seats=? WHERE make=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, color);
                    statement.setInt(2, Integer.parseInt(price));
                    statement.setInt(3, Integer.parseInt(seats));
                    statement.setString(4, make);

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle updated successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update vehicle. Make not found.");
                    }
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating vehicle");
            }
        }

        private void viewVehicles() {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM vehicles";
                try (PreparedStatement statement = connection.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("Vehicle ID");
                    model.addColumn("Make");
                    model.addColumn("Color");
                    model.addColumn("Price");
                    model.addColumn("Seats");

                    while (resultSet.next()) {
                        int vehicleID = resultSet.getInt("vehicle_id");  // Corrected the column name
                        String make = resultSet.getString("Make");
                        String color = resultSet.getString("color");
                        int price = resultSet.getInt("price");
                        int seats = resultSet.getInt("seats");

                        model.addRow(new Object[]{vehicleID, make, color, price, seats});
                    }

                    JTable dataTable = new JTable();
                    JScrollPane scrollPane = new JScrollPane(dataTable);
                    scrollPane.setBounds(550, 70, 700, 150);
                    frame.getContentPane().add(scrollPane);

                    dataTable.setModel(model);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error viewing vehicles");
            }
            }
        

        private void deleteVehicle() {
            String make = txtMake.getText().trim();

            if (make.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Make is required for deletion");
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "DELETE FROM vehicles WHERE make=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, make);

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete vehicle. Make not found.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error deleting vehicle");
            }
        }
    }

