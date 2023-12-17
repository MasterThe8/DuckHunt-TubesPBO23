package src.view;

import src.controller.GameListener;
import src.view.GamePanel;
import src.controller.Score;
import javax.swing.*;
import src.controller.CustomFont;
import src.controller.TimerLabel;
import src.controller.LabelMouseListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MainFrame extends JFrame implements GameListener {
    private JDialog loadingDialog;
    private JLayeredPane mainLayer;
    private JLabel background;
    private JLabel newGameLabel;
    private JLabel highscoreLabel;
    private JLabel exitLabel;
    private ImageIcon userAccount;
    private JLabel userAccountLabel;
    private GamePanel gamePanel;
    private Highscore highscorePanel;
    private FinishPanel finishPanel;
    private Profile profilePanel;
    private TimerLabel timerLabel;
    private Score score;

    public MainFrame() {
        initFrame();
        addListeners();

        score = new Score(finishPanel);
        finishPanel = new FinishPanel(this, score);
        gamePanel = new GamePanel(this, finishPanel, score);
        timerLabel = new TimerLabel(this);
        highscorePanel = new Highscore(this);
        profilePanel = new Profile(this);
    }

    private void initFrame() {
        this.setTitle("Duck Hunt");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        Font customFont = CustomFont.loadFont(32);
        mainLayer = new JLayeredPane();

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(new File("resources\\images\\menuPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            mainLayer.add(background, Integer.valueOf(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        userAccount = new ImageIcon("resources\\images\\accountIcon.png");
        userAccountLabel = new JLabel(userAccount);
        userAccountLabel.setBounds(715, 5, userAccount.getIconWidth(), userAccount.getIconHeight());

        userAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Profile Icon Clicked!");
                swapToProfile();
            }
        });

        newGameLabel = new JLabel("New Game");
        newGameLabel.setFont(customFont);
        newGameLabel.setForeground(Color.WHITE);
        // newGameLabel.setBackground(new Color(173, 216, 230));
        // newGameLabel.setOpaque(true);
        newGameLabel.setBounds(300,100,260,50);

        highscoreLabel = new JLabel("Highscore");
        highscoreLabel.setFont(customFont);
        highscoreLabel.setForeground(Color.WHITE);
        highscoreLabel.setBounds(295,160,270,50);

        exitLabel = new JLabel("Exit");
        exitLabel.setFont(customFont);
        exitLabel.setForeground(Color.WHITE);
        exitLabel.setBounds(370,220,120,50);

        mainLayer.add(userAccountLabel, Integer.valueOf(1));
        mainLayer.add(newGameLabel, Integer.valueOf(1));
        mainLayer.add(highscoreLabel, Integer.valueOf(1));
        mainLayer.add(exitLabel, Integer.valueOf(1));

        this.setContentPane(mainLayer);
    }

    private void addListeners() {
        highscoreLabel.addMouseListener(new LabelMouseListener(highscoreLabel, this));
        exitLabel.addMouseListener(new LabelMouseListener(exitLabel));
        newGameLabel.addMouseListener(new LabelMouseListener(newGameLabel, this));
    }

    public void swapToGamePanel() {
        setContentPane(gamePanel);
        revalidate();
        repaint();
    }

    public void swapToHighscore(){
        setContentPane(highscorePanel);
        revalidate();
        repaint();
    }

    public void swapToMainPanel(){
        setContentPane(mainLayer);
        revalidate();
        repaint();
    }

    public void swapToProfile(){
        setContentPane(profilePanel);
        revalidate();
        repaint();
    }

    public void swapToFinishPanel(){
        System.out.println("Swap to Finish");
        setContentPane(finishPanel);
        revalidate();
        repaint();
    }

    @Override
    public void endGame() {
        System.out.println("EndGame MainFrame");
        swapToFinishPanel();
        finishPanel.runUpdateScore();
    }

    @Override
    public void startGame(){
        gamePanel.startGame();
    }

    @Override
    public void onScoreChanged(int newScore) {
        throw new UnsupportedOperationException("Unimplemented method 'onScoreChanged'");
    }
    
    public static void main(String[] args) {
        MainFrame f = new MainFrame();
        f.setVisible(true);
    }

    @Override
    public void onTimerChanged(int timer) {
        throw new UnsupportedOperationException("Unimplemented method 'onTimerChanged'");
    }

    public void resetProgram(){
        dispose();

        SwingUtilities.invokeLater(() -> {
            MainFrame newFrame = new MainFrame();
            newFrame.setVisible(true);
        });
    }
}