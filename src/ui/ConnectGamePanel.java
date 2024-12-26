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

        gameClient = new GameClient(this.chessBoard);

        JPanel infoPanel = getInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        // Connect Button
        JButton connectButton = getConnectButton();
        JPanel connectButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        connectButtonPanel.add(connectButton);
        add(connectButtonPanel, BorderLayout.CENTER);
    }

    private JButton getConnectButton() {
        JButton connectButton = new GameButton("Connect to Game");
        connectButton.setPreferredSize(new Dimension(200, 40));
        connectButton.addActionListener(_ -> {
            // Set communicator for the chessboard
            this.chessBoard.setCommunicator(gameClient);
            ChessApp.layout.show(ChessApp.mainPanel, "Game");
            // Start the client communication
            gameClient.connect(ipText.getText(), Integer.parseInt(portText.getText()));
        });

        return connectButton;
    }

    private JPanel getInfoPanel() {
        // Create a new BoxLayout for the infoPanel
        JPanel infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(layout);

        // Add some padding to the panel
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 20));

        // IP Address input (editable)
        JLabel ipLabel = new GameLabel("Server IP Address: ");
        ipText = new GameTextField("127.0.0.1");
        ipText.setPreferredSize(new Dimension(150, 25));
        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ipPanel.add(ipLabel);
        ipPanel.add(ipText);
        infoPanel.add(ipPanel);

        // Port input (editable)
        JLabel portLabel = new GameLabel("Port: ");
        portText = new GameTextField("8080");
        portText.setPreferredSize(new Dimension(150, 25)); // Set a fixed width
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        portPanel.add(portLabel);
        portPanel.add(portText);
        infoPanel.add(portPanel);

        return infoPanel;
    }
}
