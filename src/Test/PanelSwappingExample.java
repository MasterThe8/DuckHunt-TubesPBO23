package src.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSwappingExample extends JFrame {

    private JPanel panel1, panel2;

    public PanelSwappingExample() {
        // Set up the JFrame
        setTitle("Panel Swap Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Panel 1 (biru)
        panel1 = new JPanel();
        panel1.setBackground(Color.BLUE);

        // Create Panel 2 (kuning)
        panel2 = new JPanel();
        panel2.setBackground(Color.YELLOW);

        // Add Panel 1 to the JFrame
        add(panel1, BorderLayout.CENTER);

        // Create Swap Button
        JButton swapButton = new JButton("Swap Panels");
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Panggil metode untuk melakukan pertukaran panel
                swapPanels();
            }
        });

        // Add Swap Button to the JFrame
        add(swapButton, BorderLayout.SOUTH);

        // Set JFrame properties
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Metode untuk melakukan pertukaran panel
    private void swapPanels() {
        // Hapus kedua panel dari JFrame
        remove(panel1);
        remove(panel2);

        // Swap antara panel1 dan panel2
        JPanel temp = panel1;
        panel1 = panel2;
        panel2 = temp;

        // Tambahkan kembali panel yang sudah ditukar ke JFrame
        add(panel1, BorderLayout.CENTER);

        // Memperbarui tampilan
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Panggil konstruktor dari kelas PanelSwappingExample
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PanelSwappingExample();
            }
        });
    }
}

