package src.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShootingGame extends JFrame {
    private static final int GAME_TIME_SECONDS = 60;

    private List<Duck> ducks;
    private Gun gun;
    private Timer timer;
    private int score;

    public ShootingGame() {
        initGame();
        initUI();
    }

    private void initGame() {
        ducks = new ArrayList<>();
        gun = new Gun();
        score = 0;
    }

    private void initUI() {
        setTitle("Shooting Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        gamePanel.addMouseListener(new GunListener());
        add(gamePanel, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(scoreLabel, BorderLayout.NORTH);

        JLabel timerLabel = new JLabel("Time: " + GAME_TIME_SECONDS);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(timerLabel, BorderLayout.SOUTH);

        timer = new Timer(1000, new TimerListener());
        timer.start();

        // Create separate threads for Duck movement and Gun shooting
        Thread duckThread = new Thread(new DuckRunnable());
        Thread gunThread = new Thread(new GunRunnable());

        duckThread.start();
        gunThread.start();
    }

    private class TimerListener implements ActionListener {
        private int remainingTime = GAME_TIME_SECONDS;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (remainingTime > 0) {
                remainingTime--;
                updateUI();
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(ShootingGame.this, "Game Over! Your Score: " + score);
            }
        }

        private void updateUI() {
            ((JLabel) getContentPane().getComponent(1)).setText("Time: " + remainingTime);
            ((JLabel) getContentPane().getComponent(0)).setText("Score: " + score);
        }
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            gun.draw(g, this);
            for (Duck duck : ducks) {
                duck.draw(g, this);
            }
        }
    }

    private class GunListener extends java.awt.event.MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            gun.shoot(evt.getX(), evt.getY());
        }
    }

    private class DuckRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                spawnDuck();
                moveDucks();
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void spawnDuck() {
            if (new Random().nextInt(100) < 10) {  // Randomly spawn ducks
                ducks.add(new Duck());
            }
        }

        private void moveDucks() {
            for (Duck duck : new ArrayList<>(ducks)) {
                duck.move();
                if (gun.isShooting(duck)) {
                    ducks.remove(duck);
                    score++;
                } else if (duck.isOutOfScreen()) {
                    ducks.remove(duck);
                }
            }
        }
    }

    private class GunRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                gun.update();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Duck {
        private static final int SIZE = 50;
        private static final int SPEED = 3;

        private int x, y;

        public Duck() {
            x = 0;
            y = new Random().nextInt(getHeight() - SIZE);
        }

        public void move() {
            x += SPEED;
        }

        public void draw(Graphics g, ImageObserver observer) {
            g.setColor(Color.RED);
            g.fillRect(x, y, SIZE, SIZE);
        }

        public boolean isOutOfScreen() {
            return x > getWidth();
        }
    }

    private class Gun {
        private int x, y;

        public Gun() {
            x = 50;
            y = getHeight() / 2;
        }

        public void shoot(int targetX, int targetY) {
            // Implement shooting logic
        }

        public boolean isShooting(Duck duck) {
            // Implement shooting logic
            return false;
        }

        public void draw(Graphics g, ImageObserver observer) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 20, 10);
        }

        public void update() {
            // Implement gun movement logic
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShootingGame().setVisible(true));
    }
}

