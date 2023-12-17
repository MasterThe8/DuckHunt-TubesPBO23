package src.view;

import java.awt.*;
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

public class Highscore extends JPanel{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/duckhunt_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private MainFrame main;
    private JList<String> highscoreList;
    private JLayeredPane mainLayer;
    private JLabel background;
    private JButton backBtn;

    public Highscore(MainFrame main){
        this.main = main;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        Font textFont = CustomFont.loadFont(24);
        Font buttonFont = CustomFont.loadFont(20);

        highscoreList = new JList<>();
        highscoreList.setBounds(50, 50, 670, 350);
        highscoreList.setFont(textFont);
        highscoreList.setForeground(Color.WHITE);
        highscoreList.setBackground(new Color(0, 0, 0, 0));
        displayScores(highscoreList);
        add(highscoreList);

        backBtn = new JButton("Back");
        backBtn.setFont(buttonFont);
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(Color.BLUE);
        backBtn.setBounds(320, 400, 160, 50);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.swapToMainPanel();
            }
        });
        add(backBtn);

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/resources/images/subPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            add(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayScores(JList<String> jList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT u.username, s.highscore FROM user u JOIN score s ON u.id = s.id ORDER BY s.highscore DESC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    int rank = 1;
                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        int highscore = resultSet.getInt("highscore");
                        model.addElement(rank + ". " + username + "   " + highscore);
                        rank++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
    
        jList.setModel(model);
    }  
}
