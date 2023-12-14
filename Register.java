package src.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import src.controller.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register extends JFrame {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/duckhunt_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private JLayeredPane mainLayer;
    private JLabel background;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel conPassLabel;
    private JPasswordField conPassField;
    private JButton registerBtn;
    private JLabel noAccLabel;
    private JLabel signInLabel;
    public Register(){
        initFrame();
        addListeners();
    }
    private void initFrame(){
        this.setTitle("Duck Hunt");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);

        mainLayer = new JLayeredPane();
        titleLabel = new JLabel();
        usernameLabel = new JLabel();
        usernameField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        conPassLabel = new JLabel();
        conPassField = new JPasswordField();
        registerBtn = new JButton();
        noAccLabel = new JLabel();
        signInLabel = new JLabel();

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(new File("resources\\images\\subPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            mainLayer.add(background, Integer.valueOf(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Font headFont = CustomFont.loadFont(32);
        Font customFont = CustomFont.loadFont(14);
        Font buttonFont = CustomFont.loadFont(20);
        Font textFont = CustomFont.loadFont(10);

        titleLabel.setText("Register");
        usernameLabel.setText("Username");
        passwordLabel.setText("Password");
        conPassLabel.setText("Confirm Password");
        registerBtn.setText("Register");
        noAccLabel.setText("or");
        signInLabel.setText("Sign In");

        titleLabel.setFont(headFont);
        usernameLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        conPassLabel.setFont(customFont);
        registerBtn.setFont(buttonFont);
        noAccLabel.setFont(textFont);
        signInLabel.setFont(textFont);

        titleLabel.setForeground(Color.WHITE);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        conPassLabel.setForeground(Color.WHITE);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBackground(Color.GREEN);
        noAccLabel.setForeground(Color.WHITE);
        signInLabel.setForeground(Color.CYAN);
        // signUpLabel.setBackground(new Color(173, 216, 230));
        // signUpLabel.setOpaque(true);

        titleLabel.setBounds(300, 80, 250, 50);
        usernameLabel.setBounds(220, 170, 200, 50);
        usernameField.setBounds(350, 182, 240, 24);
        passwordLabel.setBounds(220, 215, 200, 50);
        passwordField.setBounds(350, 227, 240, 24);
        conPassLabel.setBounds(155, 260, 200, 50);
        conPassField.setBounds(350, 272, 240, 24);
        registerBtn.setBounds(405, 305, 185, 35);
        noAccLabel.setBounds(340, 350, 50, 15);
        signInLabel.setBounds(370, 350, 70, 15);

        mainLayer.add(titleLabel, Integer.valueOf(1));
        mainLayer.add(usernameLabel, Integer.valueOf(1));
        mainLayer.add(usernameField, Integer.valueOf(1));
        mainLayer.add(passwordLabel, Integer.valueOf(1));
        mainLayer.add(passwordField, Integer.valueOf(1));
        mainLayer.add(conPassLabel, Integer.valueOf(1));
        mainLayer.add(conPassField, Integer.valueOf(1));
        mainLayer.add(registerBtn, Integer.valueOf(1));
        mainLayer.add(noAccLabel, Integer.valueOf(1));
        mainLayer.add(signInLabel, Integer.valueOf(1));

        this.setContentPane(mainLayer);
    }

    private void addListeners() {
        signInLabel.addMouseListener(new SignUpLabelListener(signInLabel));

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = usernameField.getText();
                char[] inputPasswordChars = passwordField.getPassword();
                char[] inputCPasswordChars = conPassField.getPassword();
                String inputPassword = new String(inputPasswordChars);
                String inputCPassword = new String(inputCPasswordChars);
            
                int userId = getUserId(inputUsername, inputPassword, inputCPassword);
            
                if (userId != -1) {
                    String username = getUsernameById(userId);
            
                    LoginManage loginManage = new LoginManage();
                    loginManage.login(userId, username);
                    
                    JOptionPane.showMessageDialog(Register.this,
                            "Login Berhasil!",
                            "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    runMainMenu();
                } else {
                    JOptionPane.showMessageDialog(Register.this,
                            "Login Gagal!",
                            "Login Failed", JOptionPane.WARNING_MESSAGE);
                }
            }

            private int getUserId(String username, String password, String Confirm) {
                try {
                    Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                    String query = "SELECT id FROM user WHERE username = ? AND password = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);
            
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            return resultSet.next() ? resultSet.getInt("id") : -1;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
            
            private String getUsernameById(int userId) {
                try {
                    Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                    String query = "SELECT username FROM user WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, userId);
            
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            return resultSet.next() ? resultSet.getString("username") : null;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            private void runMainMenu(){
                Register.this.dispose();
                MainMenu app = new MainMenu();
                app.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Register f = new Register();
        f.setVisible(true);
    }
}

class SignUpLabelListener extends MouseAdapter {
    private JLabel label;

    public SignUpLabelListener(JLabel label) {
        this.label = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Implement your sign-up label action here
        System.out.println("Sign In label clicked");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setHandCursor(label);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setDefaultCursor(label);
    }

    private void setHandCursor(JLabel label) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setDefaultCursor(JLabel label) {
        label.setCursor(Cursor.getDefaultCursor());
    }
}