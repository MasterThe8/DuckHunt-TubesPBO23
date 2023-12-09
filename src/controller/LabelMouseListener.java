package src.controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class LabelMouseListener implements MouseListener {
    private JLabel label;

    public LabelMouseListener(JLabel label) {
        this.label = label;
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
        setHandCursor(label);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setDefaultCursor(label);
    }

    private void setHandCursor(JLabel label) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setDefaultCursor(JLabel label) {
        label.setCursor(Cursor.getDefaultCursor());
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
    }

    private void showHighscore() {
        System.out.println("Showing Highscore...");
    }

    private void exitGame() {
        System.exit(0);
    }
}

