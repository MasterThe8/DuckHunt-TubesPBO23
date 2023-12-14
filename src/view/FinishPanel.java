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
import javax.imageio.ImageIO;
import javax.swing.*;
import src.controller.CustomFont;
import src.controller.GameListener;
import src.controller.LabelMouseListener;
import src.controller.Score;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishPanel extends JPanel implements GameListener{
    private JLabel finishLabel;
    private JLabel scoreLabel;
    private JButton retryBtn;
    private JLabel retryLabel;
    private JButton backBtn;
    private Image backgroundImg;
    private int finalScore;
    private Score scoreClass;
    private GamePanel gamePanel;
    private MainFrame main;

    public FinishPanel(MainFrame mainFrame) {
        setLayout(null);
        this.main = mainFrame;
        initFrame();
        gamePanel = new GamePanel(main, this);
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

        retryBtn = new JButton();
        retryBtn.setText("Retry");
        retryBtn.setFont(buttonFont);
        retryBtn.setForeground(Color.BLACK);
        retryBtn.setBackground(Color.YELLOW);
        retryBtn.setBounds(320, 240, 160, 50);

        backBtn = new JButton();
        backBtn.setText("back");
        backBtn.setFont(buttonFont);
        backBtn.setForeground(Color.BLACK);
        backBtn.setBackground(Color.BLUE);
        backBtn.setBounds(320, 300, 160, 50);

        retryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        add(finishLabel);
        add(scoreLabel);
        add(retryBtn);
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
    }

    @Override
    public void startGame(){
        System.out.println("Retry...");
        main.swapToGamePanel();
        gamePanel.startGame();
        scoreClass.resetScore();
    }

    @Override
    public void endGame(){

    }
}