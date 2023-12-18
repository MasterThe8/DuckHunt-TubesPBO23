package src.controller;

import javax.swing.*;

import src.view.MainFrame;
import src.view.GamePanel;
import java.awt.*;

public class TimerLabel implements Runnable {
    private JLabel timerLabel;
    private int seconds;

    private GameListener gameListener;

    private volatile boolean stopped = false;

    public TimerLabel(GameListener gameListener) {
        this.gameListener = gameListener;
        initialize();
    }

    private void initialize() {
        timerLabel = new JLabel("60");
        Font customFont = CustomFont.loadFont(24);
        timerLabel.setFont(customFont);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(700, 510, 260, 50);
        // seconds = 60;
        seconds = 10;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    private void updateTimerText(String text) {
        System.out.println("Updating timer text: " + text);
        SwingUtilities.invokeLater(() -> {
            System.out.println("Setting text on EDT");
            timerLabel.setText(text);
        });
    }
    

    public void gameFinish() {
        System.out.println("Finish cuy");
    }

    @Override
    public void run() {
        while (seconds >= 0 && !stopped) { 
            updateTimerText(String.format("%02d", seconds));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (seconds == 10) {
                seconds = 9;
            } else {
                seconds--;
            }
        }

        if (!stopped) {
            gameFinish();
            gameListener.endGame();
        }
    }

    public void reset() {
        stopped = false;
        // seconds = 60;
        seconds = 10;
    }

    public void stop() {
        System.out.println("Thread Timer Stopped");
        stopped = true;
    }
}

