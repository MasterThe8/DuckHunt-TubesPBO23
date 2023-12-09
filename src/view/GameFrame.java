package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame {
    private JLayeredPane layeredPane;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JLabel userAccountLabel;
    private JLabel newGameLabel;
    private JLabel highscoreLabel;
    private JLabel exitLabel;
    private ImageIcon userAccount;

    public GameFrame() {
        initFrame();
    }

    private void initFrame() {
        this.setTitle("Duck Hunt");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        layeredPane = new JLayeredPane();

        // Background image
        try {
            Image backgroundImage = ImageIO.read(new File("resources\\images\\menuPanelBackground.png"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel(new BorderLayout());
        userAccount = new ImageIcon("resources\\images\\accountIcon.png");
        userAccountLabel = new JLabel(userAccount);
        mainPanel.add(userAccountLabel, BorderLayout.NORTH);

        menuPanel = new JPanel(new GridLayout(3, 1));
        newGameLabel = new JLabel("New Game");
        highscoreLabel = new JLabel("Highscore");
        exitLabel = new JLabel("Exit");

        menuPanel.add(newGameLabel);
        menuPanel.add(highscoreLabel);
        menuPanel.add(exitLabel);

        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // Add mainPanel to the layeredPane
        layeredPane.add(mainPanel, JLayeredPane.PALETTE_LAYER);

        // Set the layeredPane as the content pane
        this.setContentPane(layeredPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame mainMenu = new GameFrame();
            mainMenu.setVisible(true);
        });
    }
}

