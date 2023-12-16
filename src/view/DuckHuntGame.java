package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * DuckHuntGame class represents a simple Duck Hunt game with Java GUI.
 */
public class DuckHuntGame extends JFrame {

    private JLabel scoreLabel;
    private int score;

    /**
     * Constructor to initialize the Duck Hunt game.
     */
    public DuckHuntGame() {
        setTitle("Duck Hunt Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        // Create the score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        // Create the game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * GamePanel class represents the panel where the game is played.
     */
    private class GamePanel extends JPanel {

        private ImageIcon duckIcon;
        private int duckX, duckY;

        /**
         * Constructor to initialize the game panel.
         */
        public GamePanel() {
            setLayout(null);

            // Load the duck image
            duckIcon = new ImageIcon("duck.png");

            // Set the initial position of the duck
            duckX = 350;
            duckY = 250;

            // Add mouse listener to detect clicks on the duck
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    // Check if the click is on the duck
                    if (mouseX >= duckX && mouseX <= duckX + duckIcon.getIconWidth() &&
                            mouseY >= duckY && mouseY <= duckY + duckIcon.getIconHeight()) {
                        // Increase the score and update the score label
                        score++;
                        scoreLabel.setText("Score: " + score);

                        // Move the duck to a new random position
                        duckX = (int) (Math.random() * (getWidth() - duckIcon.getIconWidth()));
                        duckY = (int) (Math.random() * (getHeight() - duckIcon.getIconHeight()));

                        // Repaint the panel to show the updated position of the duck
                        repaint();
                    }
                }
            });
        }

        /**
         * Paints the game panel and draws the duck.
         *
         * @param g The Graphics object to paint on the panel.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the duck at its current position
            g.drawImage(duckIcon.getImage(), duckX, duckY, null);
        }
    }

    /**
     * Main method to start the Duck Hunt game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DuckHuntGame());
    }
}