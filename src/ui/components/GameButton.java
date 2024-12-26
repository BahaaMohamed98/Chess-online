package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameButton extends JButton {
    private final Color backgroundColor = new Color(60, 63, 65); // Default background color
    private final Color hoverColor = new Color(80, 83, 85); // Hover background color
    private final Color pressedColor = new Color(30, 130, 230); // Pressed background color

    public GameButton() {
        setContentAreaFilled(false); // Make button area transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 18)); // Font style and size
        setForeground(Color.WHITE);
        setBackground(backgroundColor);
        setMaximumSize(new Dimension(250, 60)); // Increase the size of the buttons
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding inside the button

        // Add mouse listener for hover and pressed effects
        addMouseListener(getMouseAdapter());
    }

    public GameButton(String text) {
        this();
        setText(text);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor); // Change to hover color when mouse enters
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor); // Reset to original color
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor); // Change to pressed color
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor); // Return to hover color when released
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Enable antialiasing for smooth corners
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the button's color depending on the state
        if (getModel().isPressed()) {
            g2d.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(backgroundColor);
        }

        // Draw the rounded background with antialiasing applied
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g); // Make sure the text gets painted on top
    }
}