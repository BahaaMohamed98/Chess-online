package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GameTextField extends JTextField {

    private final Color defaultBackground = new Color(Color.white.getRGB());  // Dark background color
    private final Color focusBackground = new Color(52, 152, 219);  // Light blue background when focused

    private boolean hasFocus = false;  // Track focus state

    public GameTextField(String text) {
        super(text);

        // Set font and alignment for better readability
        setFont(new Font("Arial", Font.PLAIN, 16));
        // Default text color
        Color defaultTextColor = Color.BLACK;
        setForeground(defaultTextColor);
        setHorizontalAlignment(JTextField.CENTER);

        // Remove default border and set transparent background
        setBorder(null);
        setOpaque(false);

        // Set preferred size
        setPreferredSize(new Dimension(250, 40));

        // Focus listener to track focus changes
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                hasFocus = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing for smoother rendering
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background with rounded corners
        int arc = 15;  // Corner arc size
        g2.setColor(hasFocus ? focusBackground : defaultBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Draw border
        g2.setColor(new Color(52, 152, 219));  // Border color
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

        // Draw the text
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to avoid default border rendering
    }
}
