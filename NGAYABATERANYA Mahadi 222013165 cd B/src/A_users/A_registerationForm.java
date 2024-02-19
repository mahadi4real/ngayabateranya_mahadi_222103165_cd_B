package A_users;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class A_registerationForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFirstName;
    private JTextField textLastName;
    private JPasswordField textPassword;
    private JTextField textEmail;
    // JDBC Database URL

    private static final String DB_URL = "jdbc:mysql://localhost/cardealershipsystem_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    A_registerationForm frame = new A_registerationForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public A_registerationForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 572, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblPleaseRegisterHere = new JLabel("Please Register Here");
        lblPleaseRegisterHere.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblPleaseRegisterHere.setBounds(194, 35, 189, 30);
        contentPane.add(lblPleaseRegisterHere);
        
        JLabel lblUsers = new JLabel("Users");
        lblUsers.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        lblUsers.setBounds(249, 76, 147, 36);
        contentPane.add(lblUsers);
        
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFirstName.setBounds(116, 135, 94, 25);
        contentPane.add(lblFirstName);
        
        textFirstName = new JTextField();
        textFirstName.setBounds(231, 137, 215, 25);
        contentPane.add(textFirstName);
        textFirstName.setColumns(10);
        
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblLastName.setBounds(116, 173, 94, 25);
        contentPane.add(lblLastName);
        
        textLastName = new JTextField();
        textLastName.setBounds(231, 173, 215, 25);
        contentPane.add(textLastName);
        textLastName.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblPassword.setBounds(119, 243, 81, 25);
        contentPane.add(lblPassword);

        textPassword = new JPasswordField();
        textPassword.setBounds(231, 245, 215, 25);
        contentPane.add(textPassword);
        
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpUser();
            }
        });
        btnRegister.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        btnRegister.setBounds(100, 296, 110, 28);
        contentPane.add(btnRegister);
        
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
        btnReset.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        btnReset.setBounds(224, 296, 110, 28);
        contentPane.add(btnReset);
        
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelRegistration();
            }
        });
        btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        btnExit.setBounds(348, 296, 110, 28);
        contentPane.add(btnExit);
        
        JLabel lblRegisteredYet = new JLabel("Registered Yet?");
        lblRegisteredYet.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblRegisteredYet.setBounds(231, 353, 166, 24);
        contentPane.add(lblRegisteredYet);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginSystem loginSystem = new LoginSystem();
                loginSystem.setVisible(true);

                setVisible(false);
            }
        });
        btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        btnLogin.setBounds(231, 388, 110, 28);
        contentPane.add(btnLogin);
        
        
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblEmail.setBounds(139, 204, 61, 30);
        contentPane.add(lblEmail);
        
        textEmail = new JTextField();
        textEmail.setBounds(231, 209, 215, 25);
        contentPane.add(textEmail);
        textEmail.setColumns(10);
    }

    private void signUpUser() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO Users (First_Name, Last_Name, Email, Password) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, textFirstName.getText());
            preparedStatement.setString(2, textLastName.getText());
            preparedStatement.setString(3, textEmail.getText());
            preparedStatement.setString(4, textPassword.getText());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(contentPane, "User registered successfully!", "Registration Success",
                    JOptionPane.INFORMATION_MESSAGE);
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.setVisible(true);

            setVisible(false);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error: Unable to register user. Please try again.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void resetForm() {
        textFirstName.setText("");
        textLastName.setText("");
        textEmail.setText("");
        textPassword.setText("");
        JOptionPane.showMessageDialog(contentPane, "Form reset successful!", "Reset Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelRegistration() {
        int choice = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to cancel registration?",
                "Cancel Registration", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            setVisible(false);
        }
    }
}