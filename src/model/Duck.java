package src.model;

import javax.swing.Timer;

import src.controller.GameListener;
import src.controller.Score;
import src.controller.SoundPlayer;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Duck extends Shooting implements Runnable{
    private static final int START_Y_COORDINATE = 0;
    private static final int END_Y_COORDINATE = 390;
    private static final int NUMBER_OF_FRAMES = 2;
    private static final int ANIMATION_DELAY = 200;
    private volatile boolean stopped = false;
    private final Rectangle rectangle;
    private int speed;
    private Image[] animationFrames;
    private int currentFrame;
    private Timer animationTimer;
    private boolean isShot;
    private Image[] shotFrames;
    private SoundPlayer duckSound;
    private String duckSoundPath = "resources\\sounds\\duckDead.wav";
    private GameListener gameListener;

    public Duck() {
        rectangle = new Rectangle(80, 80);
        rectangle.setLocation(randomX(), START_Y_COORDINATE);
        speed = randomSpeed();
        loadAnimationFrames();
        initAnimationTimer();

        isShot = false;
        loadShotFrames();

        super.shoot();

        duckSound = new SoundPlayer();
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int pX) {
        this.rectangle.x = pX;
    }

    public int getX() {
        return rectangle.x;
    }

    public void setY(int pY) {
        this.rectangle.y = pY;
    }

    public int getY() {
        return rectangle.y;
    }

    private int randomX() {
        return (int) (Math.random() * 798) + 1;
    }

    private int randomSpeed() {
        return (int) (Math.random() * 2) + 2;
    }

    public void move() {
        if (isShot) {
            rectangle.translate(0, speed);
            if (rectangle.y > 400) {
                isShot = false;
                rectangle.setLocation(randomX(), START_Y_COORDINATE);
                speed = randomSpeed();
            }
        } else {
            rectangle.translate(speed, 0);

            if (rectangle.x > 800) {
                rectangle.setLocation(-rectangle.width, randomY());
                speed = randomSpeed();
            }
        }
    }

    private int randomY() {
        return (int) (Math.random() * (END_Y_COORDINATE - START_Y_COORDINATE + 1)) + START_Y_COORDINATE;
    }

    public void draw(Graphics g, ImageObserver observer) {
        if (isShot) {
            g.drawImage(getShotFrame(), getX(), getY(), observer);
        } else {
            g.drawImage(getCurrentFrame(), getX(), getY(), observer);
        }
    }

    private Image getCurrentFrame() {
        return animationFrames[currentFrame];
    }

    private Image getShotFrame() {
        return shotFrames[currentFrame];
    }

    private void animate() {
        currentFrame = (currentFrame + 1) % NUMBER_OF_FRAMES;
    }

    public void dispose() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void initAnimationTimer() {
        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animate();
            }
        });
        animationTimer.start();
    }

    private void loadAnimationFrames() {
        animationFrames = new Image[NUMBER_OF_FRAMES];
        for (int i = 0; i <= NUMBER_OF_FRAMES; i++) {
            try {
                animationFrames[i] = ImageIO.read(new File("resources\\images\\duckRight" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadShotFrames() {
        shotFrames = new Image[NUMBER_OF_FRAMES];
        for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
            try {
                shotFrames[i] = ImageIO.read(new File("resources\\images\\duckPrecipitate" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShot() {
        return isShot;
    }

    @Override
    public void shoot() {
        isShot = true;
        duckSound.playSound(duckSoundPath);
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), rectangle.width, rectangle.height);
    }
    
    @Override
    public void run() {
        while (!stopped) {
            move();
            animate();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void stop(){
        System.out.println("Thread Duck Stopped");
        stopped = true;
    }
}