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

public class Login extends JFrame {
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
    private JButton loginBtn;
    private JLabel noAccLabel;
    private JLabel signUpLabel;

    public Login(){
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
        loginBtn = new JButton();
        noAccLabel = new JLabel();
        signUpLabel = new JLabel();

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

        titleLabel.setText("Login");
        usernameLabel.setText("Username");
        passwordLabel.setText("Password");
        loginBtn.setText("Login");
        noAccLabel.setText("or");
        signUpLabel.setText("Sign Up");

        titleLabel.setFont(headFont);
        usernameLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        loginBtn.setFont(buttonFont);
        noAccLabel.setFont(textFont);
        signUpLabel.setFont(textFont);

        titleLabel.setForeground(Color.WHITE);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(Color.GREEN);
        noAccLabel.setForeground(Color.WHITE);
        signUpLabel.setForeground(Color.CYAN);
        // signUpLabel.setBackground(new Color(173, 216, 230));
        // signUpLabel.setOpaque(true);

        titleLabel.setBounds(320, 80, 200, 50);
        usernameLabel.setBounds(220, 170, 200, 50);
        usernameField.setBounds(350, 182, 240, 24);
        passwordLabel.setBounds(220, 215, 200, 50);
        passwordField.setBounds(350, 227, 240, 24);
        loginBtn.setBounds(430, 275, 160, 35);
        noAccLabel.setBounds(340, 350, 50, 15);
        signUpLabel.setBounds(370, 350, 70, 15);

        mainLayer.add(titleLabel, Integer.valueOf(1));
        mainLayer.add(usernameLabel, Integer.valueOf(1));
        mainLayer.add(usernameField, Integer.valueOf(1));
        mainLayer.add(passwordLabel, Integer.valueOf(1));
        mainLayer.add(passwordField, Integer.valueOf(1));
        mainLayer.add(loginBtn, Integer.valueOf(1));
        mainLayer.add(noAccLabel, Integer.valueOf(1));
        mainLayer.add(signUpLabel, Integer.valueOf(1));

        this.setContentPane(mainLayer);
    }

    private void addListeners() {
        signUpLabel.addMouseListener(new SignUpLabelListener(this, signUpLabel));
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = usernameField.getText();
                char[] inputPasswordChars = passwordField.getPassword();
                String inputPassword = new String(inputPasswordChars);
            
                int userId = getUserId(inputUsername, inputPassword);
            
                if (userId != -1) {
                    String username = getUsernameById(userId);
            
                    LoginSession logSession = new LoginSession();
                    logSession.login(userId, username);
                    
                    JOptionPane.showMessageDialog(Login.this,
                            "Login Berhasil!",
                            "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    runMainMenu();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Login Gagal!",
                            "Login Failed", JOptionPane.WARNING_MESSAGE);
                }
            }

            private int getUserId(String username, String password) {
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
                Login.this.dispose();
                MainFrame app = new MainFrame();
                app.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Login f = new Login();
        f.setVisible(true);
    }
}

class SignUpLabelListener extends MouseAdapter {
    private JLabel label;
    private Login login;
    private Register register;

    public SignUpLabelListener(Login log, JLabel label) {
        this.login = log;
        this.label = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Sign Up label clicked");
        login.dispose();

        register = new Register();
        register.setVisible(true);
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