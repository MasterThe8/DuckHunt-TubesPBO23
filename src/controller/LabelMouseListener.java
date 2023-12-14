package src.controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import src.view.GamePanel;
import src.view.MainFrame;

public class LabelMouseListener implements MouseListener {
    private JLabel label;
    private MainFrame mainFrame;
    private GamePanel gamePanel;
    private TimerLabel timerLabel;
    private JButton btn;

    public LabelMouseListener(JLabel label, MainFrame mainFrame) {
        this.label = label;
        this.mainFrame = mainFrame;
    }

    public LabelMouseListener(JLabel label) {
        this.label = label;
    }

    public LabelMouseListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public LabelMouseListener(JButton jbutton) {
        this.btn = jbutton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClicked();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (label != null) {
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (label != null) {
            label.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void handleMouseClicked() {
        System.out.println(label.getText() + " Clicked");
        if (label.getText().equals("New Game")) {
            startNewGame();
        } else if (label.getText().equals("Highscore")) {
            showHighscore();
        } else if (label.getText().equals("Exit")) {
            exitGame();
        }
    }

    private void startNewGame() {
        System.out.println("Starting New Game...");
        if (mainFrame != null) {
            mainFrame.swapToGamePanel();
            mainFrame.startGame();
        } else {
            System.out.println("mainFrame is null");
        }
    }

    private void showHighscore() {
        System.out.println("Showing Highscore...");
    }

    private void exitGame() {
        System.exit(0);
    }
}

