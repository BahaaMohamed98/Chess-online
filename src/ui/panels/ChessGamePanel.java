package ui.panels;

import game.ChessBoard;
import ui.ChessApp;
import ui.components.GameButton;
import ui.components.GameLabel;

import javax.swing.*;
import java.awt.*;

public class ChessGamePanel extends JPanel {
    private final ChessBoard chessBoard;

    public ChessGamePanel(ChessBoard chessBoard) {
        setLayout(new BorderLayout());
        this.chessBoard = chessBoard;

        // Header Panel
        JPanel headerPanel = getHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel chessBoardContainer = getChessBoardContainer();
        add(chessBoardContainer, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = getFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private static JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Chess Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel getChessBoardContainer() {
        // Chessboard Panel with Padding
        JPanel chessBoardContainer = new JPanel(new GridBagLayout());
        chessBoardContainer.setBackground(new Color(50, 50, 50));
        chessBoardContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ensure the ChessBoard maintains its preferred size
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        chessBoardContainer.add(chessBoard, gbc);

        return chessBoardContainer;
    }

    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(60, 63, 65));
        footerPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(90, 90, 90)));

        JLabel playerLabel = getPlayerLabel();
        footerPanel.add(playerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = getButtonPanel();
        footerPanel.add(buttonPanel, BorderLayout.SOUTH);

        return footerPanel;
    }

    private JLabel getPlayerLabel() {
        JLabel playerLabel = new GameLabel("Player: BahaaMohamed98", SwingConstants.CENTER);
        playerLabel.setBackground(new Color(60, 63, 65)); // Match the button background color

        return playerLabel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // Horizontal buttons with more spacing
        buttonPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(), 80)); // Increase height
        buttonPanel.setOpaque(true); // Ensure button panel is opaque
        buttonPanel.setBackground(new Color(50, 50, 50)); // Darker gray background for the button panel

        // Add padding to the bottom of the button panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Padding (top, left, bottom, right)
        addButtons(buttonPanel);

        return buttonPanel;
    }

    private void addButtons(JPanel buttonPanel) {
        // Menu button with a border
        JButton menuButton = new GameButton("Menu");
        menuButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add white border
        menuButton.addActionListener(_ -> ChessApp.showMenu());
        buttonPanel.add(menuButton);

        // Restart button with a border
        JButton restartButton = new GameButton("Restart");
        restartButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add white border
        restartButton.addActionListener(_ -> chessBoard.reset(true));
        buttonPanel.add(restartButton);

        // Exit button with a border
        JButton exitButton = new GameButton("Exit");
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add white border
        exitButton.addActionListener(_ -> ChessApp.exit());
        buttonPanel.add(exitButton);
    }
}