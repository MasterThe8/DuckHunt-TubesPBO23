package src.view;

import javax.swing.*;

import src.controller.GameListener;
import src.controller.Score;
import src.controller.TimerLabel;
import src.model.Duck;
import src.model.Gun;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements MouseMotionListener, ActionListener , GameListener{
    private static final Cursor CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(), "null");

    private Image backgroundImg;
    private Image cursorImg;
    private Rectangle cursorRectangle;
    private MainFrame main;
    private List<Duck> ducks;
    private Gun gun;
    private Duck duck;
    private Score scoreLabel;
    private TimerLabel timerLabel;
    private FinishPanel finishPanel;

    public GamePanel(MainFrame mainFrame, FinishPanel finishPanel, Score scoreLabel) {
        this.main = mainFrame;
        this.finishPanel = finishPanel;
        this.scoreLabel = scoreLabel;
        scoreLabel.addScoreListener(this);

        Timer timer = new Timer(10, this);
        timer.start();

        ducks = new ArrayList<>();
        createDucks();

        gun = new Gun();
        gun.drawBullet(getGraphics(), this, 3);

        duck = new Duck();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    handleMouseClick(e.getX(), e.getY());
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    gun.reload();
                }
            }
        });

        initFrame();
    }

    private void initFrame() {
        this.setLayout(null);
        this.setCursor(CURSOR);
        this.addMouseMotionListener(this);

        cursorRectangle = new Rectangle();

        try {
            backgroundImg = ImageIO.read(new File("resources/images/gameBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            cursorImg = ImageIO.read(new File("resources/images/gunsight.png"));
            cursorRectangle = new Rectangle(cursorImg.getWidth(null), cursorImg.getHeight(null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // scoreLabel = new Score(finishPanel);
        add(scoreLabel.getScoreLabel());

        timerLabel = new TimerLabel(this);
        add(timerLabel.getTimerLabel());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursorRectangle.x = e.getPoint().x - cursorRectangle.width / 2;
        cursorRectangle.y = e.getPoint().y - cursorRectangle.height / 2;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.drawImage(backgroundImg, 0, 0, this);
        g2D.drawImage(cursorImg, this.cursorRectangle.x, this.cursorRectangle.y, this);
        
        for (Duck duck : ducks) {
            duck.draw(g, this);
        }

        for (int i = 0; i < gun.getAmmo(); i++) {
            gun.drawBullet(g, this, i);
        }
    }

    private void createDucks() {
        for (int i = 0; i < 5; i++) {
            ducks.add(new Duck());
        }
    }

    private void handleMouseClick(int x, int y) {
        int ammo = gun.getAmmo();

        if (ammo != 0){
            for (Duck duck : ducks) {
                if (duck.getBounds().contains(x, y)) {
                    duck.shoot();
                    scoreLabel.increaseScore();
                    break;
                }
            }
            gun.shoot();
            repaint();
        } else {
            System.out.println("NO AMMO!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Duck duck : ducks) {
            duck.move();
        }
        repaint();
    }

    public void setFinishPanel(FinishPanel finishPanel) {
        this.finishPanel = finishPanel;
    }

    @Override
    public void startGame() {
        Thread gunThread = new Thread();
        Thread duckThread = new Thread();
        Thread timeThread = new Thread(timerLabel);

        gunThread.start();
        duckThread.start();
        timeThread.start();

        System.out.println("Start Game...");
    }

    @Override
    public void endGame(){
        gun.stop();
        duck.stop();
        timerLabel.stop();

        main.endGame();
    }

    @Override
    public void onScoreChanged(int newScore) {
        SwingUtilities.invokeLater(() -> {
            scoreLabel.getScoreLabel().setText("Score: " + newScore);
        });
    }

    @Override
    public void onTimerChanged(int timer) {
        
    }
}