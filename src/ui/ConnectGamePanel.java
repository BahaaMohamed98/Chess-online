package ui;

import game.chessBoard;
import networking.GameClient;
import ui.components.GameButton;
import ui.components.GameLabel;
import ui.components.GameTextField;

import javax.swing.*;
import java.awt.*;

public class ConnectGamePanel extends JPanel {
    game.chessBoard chessBoard;
    GameClient gameClient;
    JTextField ipText, portText;

    public ConnectGamePanel(chessBoard chessBoard) {
        this.chessBoard = chessBoard;  // Create a new chessboard instance

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Match the game panel background

        gameClient = new GameClient(this.chessBoard);

        JPanel infoPanel = getInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = getButtonsPanel();
        add(buttonsPanel, BorderLayout.CENTER);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(new Color(30, 30, 30)); // Match background color

        // Connect Button
        JButton connectButton = getConnectButton();
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(connectButton);

        // Add some vertical spacing between buttons
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Back to the Main Menu Button
        JButton backButton = getBackButton();
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(backButton);

        return buttonsPanel;
    }

    private JButton getConnectButton() {
        JButton connectButton = new GameButton("Connect to Game");
        connectButton.setPreferredSize(new Dimension(250, 40));
        connectButton.addActionListener(_ -> {
            // Set communicator for the chessboard
            this.chessBoard.setCommunicator(gameClient);

            // reset the board to start a new game
            this.chessBoard.reset(false);

            // Set the player color to black
            this.chessBoard.setPlayerColor("black");
            
            // Switch to the game panel
            ChessApp.layout.show(ChessApp.mainPanel, "Game");

            // Start the client communication
            gameClient.connect(ipText.getText(), Integer.parseInt(portText.getText()));
        });

        return connectButton;
    }

    private JButton getBackButton() {
        JButton backButton = new GameButton("Menu");
        backButton.setPreferredSize(new Dimension(250, 40));
        backButton.addActionListener(_ -> ChessApp.layout.show(ChessApp.mainPanel, "Menu"));

        return backButton;
    }

    private JPanel getInfoPanel() {
        // Create a new BoxLayout for the infoPanel
        JPanel infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(layout);
        infoPanel.setBackground(new Color(30, 30, 30)); // Match background color

        // Add some padding to the panel
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 20));

        // IP Address input (editable)
        JLabel ipLabel = new GameLabel("Server IP Address: ");
        ipText = new GameTextField("127.0.0.1");
        ipText.setPreferredSize(new Dimension(150, 25));
        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ipPanel.setBackground(new Color(30, 30, 30)); // Match background color
        ipPanel.add(ipLabel);
        ipPanel.add(ipText);
        infoPanel.add(ipPanel);

        // Port input (editable)
        JLabel portLabel = new GameLabel("Port: ");
        portText = new GameTextField("8080");
        portText.setPreferredSize(new Dimension(150, 25)); // Set a fixed width
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        portPanel.setBackground(new Color(30, 30, 30)); // Match background color
        portPanel.add(portLabel);
        portPanel.add(portText);
        infoPanel.add(portPanel);

        return infoPanel;
    }
}