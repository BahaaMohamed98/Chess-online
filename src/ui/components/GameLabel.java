package ui.components;

import javax.swing.*;
import java.awt.*;

public class GameLabel extends JLabel {
    private Color defaultColor;
    private final Color selectedColor;  // Color when the label is selected

    // Constructor that only takes text
    public GameLabel(String text) {
        super(text); // Initialize JLabel with text
        this.defaultColor = new Color(44, 62, 80); // Dark blue-gray background
        this.selectedColor = new Color(34, 153, 84); // Light green when selected
        setOpaque(false); // Set opaque to false allowing custom background rendering
        setForeground(Color.WHITE); // White text color for contrast

        // Set a larger font size for better visibility
        setFont(new Font("Arial", Font.BOLD, 14)); // Larger and bold font

        // Add padding for extra spacing around the text
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Adds padding to all sides
    }

    // Constructor that takes both text and alignment
    public GameLabel(String text, int alignment) {
        this(text); // Call the other constructor to set up text and default color
        setHorizontalAlignment(alignment); // Set the alignment for the label's text
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        // Only update the default color if the background is not the selected color
        if (!bg.equals(selectedColor)) {
            this.defaultColor = bg;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a rounded rectangle background
        g2.setColor(defaultColor);
        int arc = 20; // Corner arc size
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Draw border
        g2.setColor(selectedColor); // Use selected color for the border
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

        // Paint the label's text on top
        super.paintComponent(g);
    }
}
