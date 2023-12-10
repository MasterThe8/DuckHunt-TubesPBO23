package src.view;

// import src.controller.GameListener;
import javax.swing.*;
import src.controller.CustomFont;
import src.controller.LabelMouseListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// public class MainMenu extends JFrame implements GameListener{
public class MainMenu extends JFrame {
    private JLayeredPane mainLayer;
    private JLabel background;
    private JLabel newGameLabel;
    private JLabel highscoreLabel;
    private JLabel exitLabel;
    private ImageIcon userAccount;
    private JLabel userAccountLabel;

    public MainMenu() {
        initFrame();
        addListeners();
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

    // @Override
    // public void gameIsFinished(){
    //     goBackAction.setEnabled(true);
    // }

    private void addListeners() {
        userAccountLabel.addMouseListener(new LabelMouseListener(userAccountLabel));
        newGameLabel.addMouseListener(new LabelMouseListener(newGameLabel));
        highscoreLabel.addMouseListener(new LabelMouseListener(highscoreLabel));
        exitLabel.addMouseListener(new LabelMouseListener(exitLabel));
    }
    
    public static void main(String[] args) {
        MainMenu f = new MainMenu();
        f.setVisible(true);
    }
}
