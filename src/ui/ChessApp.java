package ui;

import game.ChessGamePanel;
import game.chessBoard;
import ui.components.GameButton;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessApp extends JFrame {
    static public JPanel mainPanel;
    static public CardLayout layout;
    private final chessBoard chessBoard;

    public ChessApp() {
        setTitle("Chess");
        setSize(650, 800);
        setMinimumSize(new Dimension(600, 800));
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/gameIcon.png")));
        setIconImage(imageIcon.getImage());

        chessBoard = new chessBoard();
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        addPanels();

        setVisible(true);
    }

    private void addPanels() {
        JPanel menuPanel = getMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        JPanel gamePanel = new ChessGamePanel(chessBoard);
        mainPanel.add(gamePanel, "Game");

        JPanel hostGamePanel = new HostGamePanel(chessBoard);
        mainPanel.add(hostGamePanel, "Host");

        JPanel connectGamePanel = new ConnectGamePanel(chessBoard);
        mainPanel.add(connectGamePanel, "Connect");

        add(mainPanel);
    }

    private static JPanel getMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        menuPanel.setBackground(new Color(30, 30, 30)); // Match the game panel background

        JLabel titleLabel = getTitleLabel();
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure the title is centered
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        JButton startButton = new GameButton("Start game");
        startButton.addActionListener(_ -> layout.show(mainPanel, "Game"));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        menuPanel.add(startButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        JButton hostGameButton = new GameButton("Host game");
        hostGameButton.addActionListener(_ -> layout.show(mainPanel, "Host"));
        hostGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        menuPanel.add(hostGameButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        JButton connectGameButton = new GameButton("Connect to game");
        connectGameButton.addActionListener(_ -> layout.show(mainPanel, "Connect"));
        connectGameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        menuPanel.add(connectGameButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Add vertical padding

        JButton exitButton = new GameButton("Exit");
        exitButton.addActionListener(_ -> System.exit(0));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        menuPanel.add(exitButton);

        return menuPanel;
    }

    private static JLabel getTitleLabel() {
        JLabel titleLabel = new JLabel("Chess Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0)); // Add padding below the title

        return titleLabel;
    }

    public static void show(JPanel panel, String name) {
        layout.show(panel, name);
    }

    public static void main(String[] args) {
        new ChessApp();
    }
}
