package src.controller;

public abstract class GameController implements GameListener{
    public abstract void performGameAction();

    protected abstract void initializeGame();

    public abstract void startGame();

    public abstract void endGame();

    protected abstract void stopGame();

    // @Override
    // public void gameIsFinished() {
    //     stopGame();
    //     showFinishPanel();
    // }

    // @Override
    // public void backMainMenu() {
    //     stopGame();
    //     showMainMenu();
    // }

    protected abstract void showFinishPanel();

    protected abstract void showMainMenu();
}
