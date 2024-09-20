package interf;

import mycode.dbconnect;
import mycode.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class register extends javax.swing.JFrame {

    public register() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField(); // Username field
        jPasswordField1 = new javax.swing.JPasswordField(); // Password field
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username:");
        jLabel2.setText("Password:");

        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        // Layout setup (similar to login JFrame)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    // Action listener for the register button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Get user input from text fields
        String username = jTextField1.getText().trim();
        String password = new String(jPasswordField1.getPassword()).trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            registerUser(username, password); // Call the register method
        }
    }

    // Method to register a new user
    private void registerUser(String username, String password) {
        Connection conn = dbconnect.connect();
    
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String hashedPassword = PasswordUtil.hashPassword(password);
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, hashedPassword);
            int rowsInserted = pst.executeUpdate();
    
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                new login().setVisible(true);
                this.dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String message = (e.getErrorCode() == 1062)
                ? "Username already exists. Please choose another."
                : "Database error occurred!";
            JOptionPane.showMessageDialog(this, message, "Registration Failed", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration                   
}
