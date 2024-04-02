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
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class transaction {

	private JFrame frame;
	private JTextField txtSale_id;
	private JTextField txtPaymentMethod;
	private JTextField txtTransactionDate;
	private JTextField txtAmount;
	

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
					transaction window = new transaction();
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
	public transaction() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 64, 64));
		frame.getContentPane().setLayout(null);
		
		JLabel lblTransaction = new JLabel("TRANSACTION");
		lblTransaction.setForeground(new Color(0, 0, 0));
		lblTransaction.setFont(new Font("Montserrat", Font.BOLD, 26));
		lblTransaction.setBounds(255, 34, 209, 55);
		frame.getContentPane().add(lblTransaction);
		
		JLabel lblSale_id = new JLabel("Sale_id");
		lblSale_id.setForeground(new Color(0, 0, 0));
		lblSale_id.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblSale_id.setBounds(52, 143, 92, 27);
		frame.getContentPane().add(lblSale_id);
		
		JLabel lblPaymentMethod = new JLabel("PaymentMethod");
		lblPaymentMethod.setForeground(new Color(0, 0, 0));
		lblPaymentMethod.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblPaymentMethod.setBounds(52, 190, 203, 33);
		frame.getContentPane().add(lblPaymentMethod);
		
		JLabel lblTransactionDate = new JLabel("TransactionDate");
		lblTransactionDate.setForeground(new Color(0, 0, 0));
		lblTransactionDate.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblTransactionDate.setBounds(52, 247, 203, 27);
		frame.getContentPane().add(lblTransactionDate);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setForeground(new Color(0, 0, 0));
		lblAmount.setFont(new Font("Montserrat", Font.PLAIN, 22));
		lblAmount.setBounds(52, 303, 119, 27);
		frame.getContentPane().add(lblAmount);
		
		txtSale_id = new JTextField();
		txtSale_id.setBounds(300, 143, 227, 33);
		frame.getContentPane().add(txtSale_id);
		txtSale_id.setColumns(10);
		
		txtPaymentMethod = new JTextField();
		txtPaymentMethod.setBounds(300, 194, 227, 33);
		frame.getContentPane().add(txtPaymentMethod);
		txtPaymentMethod.setColumns(10);
		
		txtTransactionDate = new JTextField();
		txtTransactionDate.setColumns(10);
		txtTransactionDate.setBounds(300, 247, 227, 33);
		frame.getContentPane().add(txtTransactionDate);
		
		txtAmount = new JTextField();
		txtAmount.setColumns(10);
		txtAmount.setBounds(300, 297, 227, 33);
		frame.getContentPane().add(txtAmount);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnAdd.setBounds(63, 398, 89, 39);
		frame.getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnDelete.setBounds(543, 398, 119, 39);
		frame.getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnUpdate.setBounds(373, 398, 129, 39);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnViewTransactions = new JButton("View");
		btnViewTransactions.setFont(new Font("Montserrat", Font.BOLD, 24));
		btnViewTransactions.setBounds(203, 398, 129, 39);
		frame.getContentPane().add(btnViewTransactions);
		frame.setBounds(100, 100, 715, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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

		btnViewTransactions.addActionListener(new ActionListener() {
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
	    String paymentMethod = txtPaymentMethod.getText().trim();
	    String transactionDate = txtTransactionDate.getText().trim();
	    String amount = txtAmount.getText().trim();

	    if (paymentMethod.isEmpty() || transactionDate.isEmpty() || amount.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "All fields are required");
	        return;
	    }

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String sql = "INSERT INTO sales (PaymentMethod, TransactionDate, Amount) VALUES (?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, paymentMethod);
	            statement.setString(2, transactionDate);
	            statement.setString(3, amount);

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
	    int transactionId = Integer.parseInt(txtSale_id.getText().trim());
	    String paymentMethod = txtPaymentMethod.getText().trim();
	    String transactionDate = txtTransactionDate.getText().trim();
	    String amount = txtAmount.getText().trim();

	    if (transactionId <= 0) {
	        JOptionPane.showMessageDialog(null, "Transaction ID is required for updating");
	        return;
	    }

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String sql = "UPDATE sales SET PaymentMethod=?, TransactionDate=?, Amount=? WHERE transaction_id=?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, paymentMethod);
	            statement.setString(2, transactionDate);
	            statement.setString(3, amount);
	            statement.setInt(4, transactionId);

	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(null, "Sale updated successfully");
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to update sale. Transaction ID not found.");
	            }
	        }
	    } catch (SQLException | NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error updating sale");
	    }
	}

	private void viewSales() {
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String sql = "SELECT * FROM sales";
	        try (PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {

	            DefaultTableModel model = new DefaultTableModel();
	            model.addColumn("Transaction ID");
	            model.addColumn("Payment Method");
	            model.addColumn("Transaction Date");
	            model.addColumn("Amount");

	            while (resultSet.next()) {
	                int transactionId = resultSet.getInt("transaction_id");
	                String paymentMethod = resultSet.getString("PaymentMethod");
	                String transactionDate = resultSet.getString("TransactionDate");
	                String amount = resultSet.getString("Amount");

	                model.addRow(new Object[]{transactionId, paymentMethod, transactionDate, amount});
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
	    int transactionId = Integer.parseInt(txtSale_id.getText().trim());

	    if (transactionId <= 0) {
	        JOptionPane.showMessageDialog(null, "Transaction ID is required for deletion");
	        return;
	    }

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String sql = "DELETE FROM sales WHERE transaction_id=?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, transactionId);

	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(null, "Sale deleted successfully");
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to delete sale. Transaction ID not found.");
	            }
	        }
	    } catch (SQLException | NumberFormatException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error deleting sale");
	    }
	}
}