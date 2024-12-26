package ui;

import game.chessBoard;
import networking.GameHost;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostGamePanel extends JPanel {

    game.chessBoard chessBoard;
    GameHost gameHost;
    JTextField portText;

    public HostGamePanel(chessBoard chessBoard) {
        this.chessBoard = chessBoard;

        setLayout(new BorderLayout());

        gameHost = new GameHost(this.chessBoard);

        JPanel infoPanel = getInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        JButton hostButton = new GameButton("Host Game");
        hostButton.setPreferredSize(new Dimension(200, 40));
        hostButton.addActionListener(_ -> {
            this.chessBoard.setCommunicator(gameHost);
            ChessApp.layout.show(ChessApp.mainPanel, "Game");
            gameHost.start(Integer.parseInt(portText.getText()));
        });
        JPanel hostButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hostButtonPanel.add(hostButton, BorderLayout.SOUTH);
        add(hostButtonPanel, BorderLayout.CENTER);
    }

    private JPanel getInfoPanel() {
        // Create a new BoxLayout specifically for the infoPanel
        JPanel infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(layout);

        // Add some padding to the panel
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 20));

        // Local IP Address display (non-editable)
        JLabel ipLabel = new JLabel("Your IP Address:  " + getLocalIPAddress(), SwingConstants.CENTER);
        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the label and text field nicely
        ipPanel.add(ipLabel);
        infoPanel.add(ipPanel);

        // Port input (editable)
        JLabel portLabel = new JLabel("Port: ");
        portText = new JTextField("8080");
        portText.setPreferredSize(new Dimension(150, 25));  // Set a fixed width
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the label and text field nicely
        portPanel.add(portLabel);
        portPanel.add(portText);
        infoPanel.add(portPanel);

        return infoPanel;
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Failed to get local IP address");
            return "";
        }
    }
}
