	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.sql.*;
	
	public class Loginpage extends JFrame {
	    private JTextField userIdField;
	    private JPasswordField passwordField;
	    private JButton loginButton;
	    private JLabel messageLabel;
	    private JLabel avatarLabel;
	
	    public Loginpage() {
	
	        setTitle("Java simple login page");
	        setSize(500, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); 
	        setLayout(new GridBagLayout());  
	
	     
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10); 
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	
	        
	        JLabel titleLabel = new JLabel("SIMPLE LOGIN PAGE", SwingConstants.CENTER);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridwidth = 2;
	        gbc.anchor = GridBagConstraints.CENTER;
	        add(titleLabel, gbc);
	
	        // Add a user avatar icoN
	        avatarLabel = new JLabel(new ImageIcon("ADD IMAGE ADDRESS HERE"));
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        gbc.gridwidth = 2;
	        gbc.anchor = GridBagConstraints.CENTER; 
	        add(avatarLabel, gbc);
	
	        // Add Labels and TextFields for User ID and Password
	        JLabel userIdLabel = new JLabel("User ID:");
	        userIdLabel.setFont(new Font("Arial", Font.BOLD, 32)); 
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.WEST;
	        add(userIdLabel, gbc);
	
	        userIdField = new JTextField(20);
	        userIdField.setFont(new Font("Arial", Font.PLAIN, 32));
	        userIdField.setPreferredSize(new Dimension(250, 60));  
	        userIdField.setToolTipText("Enter your user ID");
	        gbc.gridx = 1;
	        gbc.gridy = 2;
	        add(userIdField, gbc);
	
	        JLabel passwordLabel = new JLabel("Password:");
	        passwordLabel.setFont(new Font("Arial", Font.BOLD, 32));  
	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        add(passwordLabel, gbc);
	
	        passwordField = new JPasswordField(20);
	        passwordField.setFont(new Font("Arial", Font.PLAIN, 32));  
	        passwordField.setPreferredSize(new Dimension(250, 60));  
	        passwordField.setToolTipText("Enter your password");
	        gbc.gridx = 1;
	        gbc.gridy = 3;
	        add(passwordField, gbc);
	
	        // Add Login Button
	        loginButton = new JButton("Login");
	        loginButton.setFont(new Font("Arial", Font.BOLD, 36)); 
	        loginButton.setBackground(new Color(51, 153, 255));  
	        loginButton.setForeground(Color.WHITE);
	        loginButton.addActionListener(new LoginAction());
	        gbc.gridx = 1;
	        gbc.gridy = 4;
	        gbc.anchor = GridBagConstraints.EAST;
	        add(loginButton, gbc);
	
	    
	        messageLabel = new JLabel("");
	        messageLabel.setFont(new Font("Arial", Font.PLAIN, 28)); 
	        messageLabel.setForeground(Color.RED);
	        gbc.gridx = 1;
	        gbc.gridy = 5;
	        gbc.anchor = GridBagConstraints.CENTER;
	        add(messageLabel, gbc);
	
	        getContentPane().setBackground(new Color(30, 30, 30));  // Dark background
	        titleLabel.setForeground(Color.WHITE);
	        userIdLabel.setForeground(Color.WHITE);
	        passwordLabel.setForeground(Color.WHITE);
	        messageLabel.setForeground(Color.WHITE);
	        setVisible(true);
	    }
	
	    private class LoginAction implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String userId = userIdField.getText();
	            String password = new String(passwordField.getPassword());
	
	            if (userId.isEmpty() || password.isEmpty()) {
	                messageLabel.setText("Please enter both fields.");
	                messageLabel.setForeground(Color.RED);
	            } else {
	                try {
	                    boolean loginSuccessful = checkCredentials(userId, password);
	                    if (loginSuccessful) {
	                        int id = Integer.parseInt(userId);
	                        if (id >= 1 && id <= 20) {
	                            messageLabel.setText("Logged in as User.");
	                            messageLabel.setForeground(Color.GREEN);
	                        } else if (id >= 100 && id <= 120) {
	                            messageLabel.setText("Logged in as Industry.");
	                            messageLabel.setForeground(Color.GREEN);
	                        } else {
	                            messageLabel.setText("Invalid User ID.");
	                            messageLabel.setForeground(Color.RED);
	                        }
	                    } else {
	                        messageLabel.setText("Invalid credentials.");
	                        messageLabel.setForeground(Color.RED);
	                    }
	                } catch (SQLException ex) {
	                    messageLabel.setText("Database error.");
	                    messageLabel.setForeground(Color.RED);
	                }
	            }
	        }
	
	        private boolean checkCredentials(String userId, String password) throws SQLException {
	        	
	
	        	//CHANGE DATABASE NAME!!
	            String url = "jdbc:mysql://localhost:3306/Database name";
	            String dbUsername = "root";  // Use your MySQL username
	            String dbPassword = "root";  // Use your MySQL password
	
	            // Establish connection
	            try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
	                // UPDATE YOUR TABLE NAME
	            	String query = "SELECT password FROM TABLE NAME WHERE username = ?";
	                try (PreparedStatement statement = connection.prepareStatement(query)) {
	                    statement.setString(1, userId);
	                    try (ResultSet resultSet = statement.executeQuery()) {
	                        if (resultSet.next()) {
	                            String storedPassword = resultSet.getString("password");
	                            return password.equals(storedPassword);  // Check if passwords match
	                        }
	                    }
	                }
	            }
	            return false;  
	        }
	    }
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(Loginpage::new); 
	    }
	}
