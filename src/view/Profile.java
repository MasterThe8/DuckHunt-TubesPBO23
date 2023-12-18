package src.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;

import src.controller.CustomFont;
import src.controller.LoginSession;

public class Profile extends JPanel{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/duckhunt_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private MainFrame main;
    private JLayeredPane mainLayer;
    private JLabel userLabel;
    private JLabel scoreLabel;
    private JLabel background;
    private JButton backBtn;
    private JButton logoutBtn;

    public Profile(MainFrame main){
        this.main = main;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        mainLayer = new JLayeredPane();
        
        Font labelFont = CustomFont.loadFont(32);
        Font buttonFont = CustomFont.loadFont(20);

        LoginSession userId = new LoginSession();
        userLabel = new JLabel();
        userLabel.setFont(labelFont);
        userLabel.setText(userId.getUsername());
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(280, 80, 400, 50);
        add(userLabel);

        scoreLabel = new JLabel();
        scoreLabel.setFont(labelFont);
        scoreLabel.setText(String.valueOf(getUserScore(userId.getId())));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBounds(320, 140, 160, 50);
        add(scoreLabel);

        backBtn = new JButton("Back");
        backBtn.setFont(buttonFont);
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(Color.BLUE);
        backBtn.setBounds(220, 300, 160, 50);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.swapToMainPanel();
            }
        });
        add(backBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(buttonFont);
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setBounds(420, 300, 160, 50);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });
        add(logoutBtn);

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/resources/images/subPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            add(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void logOut(){
        LoginSession loginSession = new LoginSession();
        loginSession.login(0,"");

        main.dispose();
        Landing f = new Landing();
        f.setVisible(true);
    }

    private int getUserScore(int userId) {
        int userScore = -1;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT highscore FROM score WHERE id = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userScore = resultSet.getInt("highscore");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userScore;
    }
}