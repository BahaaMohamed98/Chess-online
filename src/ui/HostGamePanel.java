package ui;

import game.chessBoard;
import networking.GameHost;
import ui.components.GameButton;
import ui.components.GameLabel;
import ui.components.GameTextField;

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

        // Set layout and background color
        setLayout(new BorderLayout());
        setBackground(new Color(44, 62, 80)); // Dark blue-gray color

        gameHost = new GameHost(this.chessBoard);

        // Add info panel to the North region of the BorderLayout
        JPanel infoPanel = getInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        // Add host button panel to the center region of the BorderLayout
        JPanel hostButtonPanel = getHostButtonPanel();
        add(hostButtonPanel, BorderLayout.CENTER);
    }

    private JPanel getHostButtonPanel() {
        // Create the "Host Game" button
        JButton hostButton = new GameButton("Host Game");
        hostButton.setPreferredSize(new Dimension(200, 40));

        hostButton.addActionListener(_ -> {
            // Start the game host and show the game panel
            this.chessBoard.setCommunicator(gameHost);
            ChessApp.layout.show(ChessApp.mainPanel, "Game");
            gameHost.start(Integer.parseInt(portText.getText()));
        });

        // Use a FlowLayout for the button panel
        JPanel hostButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hostButtonPanel.add(hostButton); // No need for BorderLayout.SOUTH here

        return hostButtonPanel;
    }

    private JPanel getInfoPanel() {
        // Create a BoxLayout for the info panel (vertical layout)
        JPanel infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(layout);

        // Add some padding to the panel
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 20));

        // Add IP label panel
        JPanel ipPanel = getIPLabel();
        infoPanel.add(ipPanel);

        // Add port panel
        JPanel portPanel = getPortPanel();
        infoPanel.add(portPanel);

        return infoPanel;
    }

    private JPanel getPortPanel() {
        // Label and text field for the port
        JLabel portLabel = new GameLabel("Port: ");
        portText = new GameTextField("8080");
        portText.setPreferredSize(new Dimension(150, 25));  // Set a fixed width for the text field

        // Panel to hold the label and text field
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the label and text field nicely
        portPanel.add(portLabel);
        portPanel.add(portText);

        return portPanel;
    }

    private JPanel getIPLabel() {
        // Display local IP address
        JLabel ipLabel = new GameLabel("Your IP Address:  " + getLocalIPAddress(), SwingConstants.CENTER);

        // Panel to hold the IP label
        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the label to the left
        ipPanel.add(ipLabel);

        return ipPanel;
    }

    private String getLocalIPAddress() {
        // Get the local IP address
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Failed to get local IP address");
            return "";
        }
    }
}
