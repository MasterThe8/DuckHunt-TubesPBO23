package src.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import src.controller.CustomFont;
import src.controller.GameListener;
import src.controller.LabelMouseListener;
import src.controller.LoginSession;
import src.controller.Score;
import src.controller.TimerLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishPanel extends JPanel implements GameListener{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/duckhunt_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private JLabel finishLabel;
    private JLabel scoreLabel;
    private JButton backBtn;
    private Image backgroundImg;
    private int finalScore;
    private Score scoreClass;
    private TimerLabel timerLabel;
    private GamePanel gamePanel;
    private MainFrame main;

    public FinishPanel(MainFrame mainFrame, Score score) {
        setLayout(null);
        this.main = mainFrame;
        this.scoreClass = score;
        initFrame();
        gamePanel = new GamePanel(main, this, scoreClass);
        scoreClass.addScoreListener(this);
        timerLabel = new TimerLabel(mainFrame);
    }

    private void initFrame() {
        this.setCursor(Cursor.getDefaultCursor());

        try {
            backgroundImg = new ImageIcon("resources/images/gameBackground.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font titleFont = CustomFont.loadFont(32);
        Font infoFont = CustomFont.loadFont(24);
        Font buttonFont = CustomFont.loadFont(20);

        finishLabel = new JLabel();
        finishLabel.setText("Finished!");
        finishLabel.setFont(titleFont);
        finishLabel.setForeground(Color.WHITE);
        finishLabel.setBounds(300, 80, 240, 50);

        scoreLabel = new JLabel();
        scoreLabel.setText("Your Score Is " + finalScore);
        scoreLabel.setFont(infoFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(260, 140, 500, 50);

        backBtn = new JButton();
        backBtn.setText("back");
        backBtn.setFont(buttonFont);
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(Color.BLUE);
        backBtn.setBounds(320, 300, 160, 50);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.resetProgram();
            }
        });

        add(finishLabel);
        add(scoreLabel);
        add(backBtn);
    }

    public void updateScoreLabel(int finalScore){
        scoreLabel.setText("Your Score Is " + finalScore);
        System.out.println("UPDATE FINAL SCORE");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.drawImage(backgroundImg, 0, 0, this);
    }

    @Override
    public void onScoreChanged(int newScore) {
        updateScoreLabel(newScore);
        setFinalScore(newScore);
    }

    @Override
    public void startGame(){
        System.out.println("Retry...");
        main.swapToGamePanel();
        gamePanel.startGame();
        scoreClass.resetScore();

        timerLabel.reset();

        Thread timerThread = new Thread(timerLabel);
        timerThread.start();
    }

    @Override
    public void endGame(){

    }

    public void runUpdateScore(){
        setFinalScore(finalScore);
        updateScore(finalScore);
    }

    private void setFinalScore(int score){
        finalScore = score;
    }


    private void updateScore(int finalScore){
        LoginSession id = new LoginSession();
        int userId = id.getId();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            if (userExists(connection, userId)) {
                int existingScore = getExistingScore(connection, userId);

                if (finalScore > existingScore) {
                    updateExistingScore(connection, userId, finalScore);
                } else {
                    System.out.println("Skor baru tidak lebih tinggi dari skor yang sudah ada. Tidak ada pembaruan dilakukan.");
                }
            } else {
                addNewScore(connection, userId, finalScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean userExists(Connection connection, int userId) throws SQLException {
        String query = "SELECT id FROM score WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private int getExistingScore(Connection connection, int userId) throws SQLException {
        int existingScore = 0;
        String query = "SELECT highscore FROM score WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    existingScore = resultSet.getInt("highscore");
                }
            }
        }
        return existingScore;
    }

    private void updateExistingScore(Connection connection, int userId, int finalScore) throws SQLException {
        String updateQuery = "UPDATE score SET highscore = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, finalScore);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Highscore updated successfully for user ID: " + userId);
            } else {
                System.out.println("Failed to update highscore. User ID not found: " + userId);
            }
        }
    }

    private void addNewScore(Connection connection, int userId, int finalScore) throws SQLException {
        String insertQuery = "INSERT INTO score (id, highscore) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, finalScore);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("New highscore added successfully for user ID: " + userId);
            } else {
                System.out.println("Failed to add new highscore. User ID: " + userId);
            }
        }
    }

    @Override
    public void onTimerChanged(int timer) {
        throw new UnsupportedOperationException("Unimplemented method 'onTimerChanged'");
    }
}