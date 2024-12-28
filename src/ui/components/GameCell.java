package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCell extends JButton {
    private Color defaultColor;

    public GameCell(Color color) {
        this.defaultColor = color;
        setContentAreaFilled(true); // Allow the background color to be filled
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setBackground(defaultColor);

        // Add mouse listener for hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateBackground(new Color(255, 255, 102)); // Change to a hover color (light yellow)
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateBackground(defaultColor); // Revert to original color when the mouse exits
            }
        });
    }

    private void updateBackground(Color bg) {
        Color current = this.defaultColor;
        setBackground(bg);
        this.defaultColor = current;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.defaultColor = bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
