package A_users;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginSystem extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
    private JTextField textEmail;
    private JPasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost/cardealershipsystem_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginSystem window = new LoginSystem();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginSystem() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setForeground(new Color(0, 0, 0));
        frame.getContentPane().setBackground(new Color(0, 64, 64));
        frame.setBounds(100, 100, 665, 445);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("LOG IN SYSTEM");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 39));
        lblNewLabel.setBounds(164, 45, 341, 33);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblUsername = new JLabel("Email");
        lblUsername.setForeground(new Color(0, 0, 0));
        lblUsername.setFont(new Font("Montserrat", Font.BOLD, 19));
        lblUsername.setBounds(102, 144, 152, 24);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(0, 0, 0));
        lblPassword.setFont(new Font("Montserrat", Font.BOLD, 19));
        lblPassword.setBounds(102, 195, 105, 24);
        frame.getContentPane().add(lblPassword);

        textEmail = new JTextField();
        textEmail.setBounds(320, 144, 171, 20);
        frame.getContentPane().add(textEmail);
        textEmail.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(320, 195, 171, 20);
        frame.getContentPane().add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Montserrat", Font.BOLD, 20));
        btnLogin.setBounds(112, 312, 116, 33);
        frame.getContentPane().add(btnLogin);

        JButton btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Montserrat", Font.BOLD, 20));
        btnReset.setBounds(275, 312, 116, 33);
        frame.getContentPane().add(btnReset);

        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Montserrat", Font.BOLD, 20));
        btnExit.setBounds(431, 312, 116, 33);
        frame.getContentPane().add(btnExit);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUserName = textEmail.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (isValidLogin(enteredUserName, enteredPassword)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textEmail.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(frame, "Form reset successful!", "Reset Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static boolean isValidLogin(String enteredUserName, String enteredPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, enteredUserName);
            preparedStatement.setString(2, enteredPassword);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
