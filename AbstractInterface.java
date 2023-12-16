
// Interface
public interface GameBehavior {
    void startGame();
    void endGame();
    void pauseGame();
}

// Abstract Class
public abstract class AbstractGame implements GameBehavior {
    private boolean isPaused;

    @Override
    public void startGame() {
        System.out.println("Game started!");
        isPaused = false;
    }

    @Override
    public void endGame() {
        System.out.println("Game ended!");
        isPaused = false;
    }

    @Override
    public void pauseGame() {
        System.out.println("Game paused!");
        isPaused = true;
    }

    // Abstract method to be implemented by concrete classes
    public abstract void performGameAction();
}

// Concrete Class
public class AbstractInterface extends AbstractGame {
    @Override
    public void performGameAction() {
        // Implementation specific to Duck Hunt game
        System.out.println("Performing Duck Hunt action!");
    }

    public static void main(String[] args) {
        DuckHuntGame duckHuntGame = new DuckHuntGame();
        duckHuntGame.startGame();
        duckHuntGame.performGameAction();
        duckHuntGame.pauseGame();
        duckHuntGame.endGame();
    }
}
