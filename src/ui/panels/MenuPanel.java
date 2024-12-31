package ui.panels;

import ui.ChessApp;
import ui.components.GameButton;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        setBackground(new Color(30, 30, 30)); // Match the game panel background

        addComponents();
    }

    private void addComponents() {
        JLabel titleLabel = getTitleLabel();
        add(titleLabel);
        add(Box.createVerticalStrut(10)); // Add vertical padding

        addButton("Start game", "Game");
        addButton("Host game", "Host");
        addButton("Connect to game", "Connect");
        addExitButton();
    }

    private void addButton(String text, String panelToShow) {
        JButton button = new GameButton(text);

        button.addActionListener(_ -> ChessApp.show(ChessApp.mainPanel, panelToShow));
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        add(button);
        add(Box.createVerticalStrut(10)); // Add vertical padding
    }

    private void addExitButton() {
        JButton exitButton = new GameButton("Exit");
        exitButton.addActionListener(_ -> System.exit(0));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        add(exitButton);
    }

    private static JLabel getTitleLabel() {
        JLabel titleLabel = new JLabel("Chess Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0)); // Add padding below the title
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure the title is centered

        return titleLabel;
    }
}
