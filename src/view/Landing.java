package src.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.controller.CustomFont;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Landing extends JFrame{
    private JLayeredPane mainLayer;
    private JLabel background;
    private JLabel title, play;

    private Login loginPanel;

    public Landing(){
        initFrame();
        addListeners();

        loginPanel = new Login();
    }

    private void initFrame(){
        this.setTitle("Duck Hunt");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        Font titleFont = CustomFont.loadFont(50);
        Font customFont = CustomFont.loadFont(24);
        mainLayer = new JLayeredPane();

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(new File("resources\\images\\menuPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            mainLayer.add(background, Integer.valueOf(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        title = new JLabel("Duck Hunt");
        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);
        title.setBounds(220, 60, 600, 50);

        play = new JLabel("Play");
        play.setFont(customFont);
        play.setForeground(Color.WHITE);
        play.setBounds(350, 220, 100, 30);

        mainLayer.add(title, Integer.valueOf(1));
        mainLayer.add(play, Integer.valueOf(1));
        
        this.setContentPane(mainLayer);
    }

    private void addListeners() {
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewLogin();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setHandCursor(play);
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                setDefaultCursor(play);
            }
        
            private void setHandCursor(JLabel label) {
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        
            private void setDefaultCursor(JLabel label) {
                label.setCursor(Cursor.getDefaultCursor());
            }

            private void viewLogin(){
                Landing.this.dispose();
                loginPanel.setVisible(true);
            }
            
        });
    }

    public static void main(String[] args) {
        Landing f = new Landing();
        f.setVisible(true);
    }
}

package src.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.controller.CustomFont;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Landing extends JFrame{
    private JLayeredPane mainLayer;
    private JLabel background;
    private JLabel title, play;

    private Login loginPanel;

    public Landing(){
        initFrame();
        addListeners();

        loginPanel = new Login();
    }

    private void initFrame(){
        this.setTitle("Duck Hunt");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        Font titleFont = CustomFont.loadFont(50);
        Font customFont = CustomFont.loadFont(24);
        mainLayer = new JLayeredPane();

        try {
            background = new JLabel(new ImageIcon(ImageIO.read(new File("resources\\images\\menuPanelBackground.png"))));
            background.setBounds(0, 0, 800, 600);
            mainLayer.add(background, Integer.valueOf(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        title = new JLabel("Duck Hunt");
        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);
        title.setBounds(220, 60, 600, 50);

        play = new JLabel("Play");
        play.setFont(customFont);
        play.setForeground(Color.WHITE);
        play.setBounds(350, 220, 100, 30);

        mainLayer.add(title, Integer.valueOf(1));
        mainLayer.add(play, Integer.valueOf(1));
        
        this.setContentPane(mainLayer);
    }

    private void addListeners() {
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewLogin();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setHandCursor(play);
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                setDefaultCursor(play);
            }
        
            private void setHandCursor(JLabel label) {
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        
            private void setDefaultCursor(JLabel label) {
                label.setCursor(Cursor.getDefaultCursor());
            }

            private void viewLogin(){
                Landing.this.dispose();
                loginPanel.setVisible(true);
            }
            
        });
    }

    public static void main(String[] args) {
        Landing f = new Landing();
        f.setVisible(true);
    }
}
