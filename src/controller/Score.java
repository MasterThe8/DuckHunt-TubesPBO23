package src.controller;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import src.view.FinishPanel;
import src.view.GamePanel;

public class Score {
    private JLabel scoreLabel;
    private int score = 0;
    private FinishPanel finishPanel;
    private List<GameListener> listeners = new ArrayList<>();

    public Score(FinishPanel finishPanel) {
        this.finishPanel = finishPanel;
        initialize();
    }

    private void initialize() {
        scoreLabel = new JLabel("Score: " + score);
        Font customFont = CustomFont.loadFont(24);
        scoreLabel.setFont(customFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(300, 510, 260, 50);
    }

    public void addScoreListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeScoreListener(GameListener listener) {
        listeners.remove(listener);
    }

    private void notifyScoreChanged() {
        for (GameListener listener : listeners) {
            listener.onScoreChanged(score);
        }
    }

    public void increaseScore() {
        score = score + 10;
        System.out.println("Score:"+score);
        updateScoreLabel();
        notifyScoreChanged();
    }

    private void updateScoreLabel() {
        System.out.println("updateScoreLabel");
        SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public int getScore() {
        return score;
    }

    public void setText(String string) {
    }

    public void resetScore(){
        score = 0;
        notifyScoreChanged();
    }
}
