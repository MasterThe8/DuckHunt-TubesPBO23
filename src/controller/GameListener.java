package src.controller;

public interface GameListener {
    void startGame();
    void endGame();
    void onScoreChanged(int newScore);
}
