package ui;

import com.github.bhlangonijr.chesslib.Side;
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
        setBackground(new Color(30, 30, 30)); // Match the game panel background

        gameHost = new GameHost(this.chessBoard);

        // Add an info panel to the North region of the BorderLayout
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

        // Host Button
        JButton hostButton = getHostButton();
        hostButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(hostButton);

        // Add some vertical spacing between buttons
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Back to the Main Menu Button
        JButton backButton = getBackButton();
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(backButton);

        return buttonsPanel;
    }

    private JButton getHostButton() {
        JButton hostButton = new GameButton("Host Game");
        hostButton.setPreferredSize(new Dimension(250, 40));

        hostButton.addActionListener(_ -> {
            // Start the game host and show the game panel
            this.chessBoard.setCommunicator(gameHost);
            this.chessBoard.reset(false);
            this.chessBoard.setPlayerSide(Side.WHITE);

            ChessApp.layout.show(ChessApp.mainPanel, "Game");
            gameHost.start(Integer.parseInt(portText.getText()));
        });

        return hostButton;
    }

    private JButton getBackButton() {
        JButton backButton = new GameButton("Menu");
        backButton.setPreferredSize(new Dimension(250, 40));
        backButton.addActionListener(_ -> ChessApp.layout.show(ChessApp.mainPanel, "Menu"));

        return backButton;
    }

    private JPanel getInfoPanel() {
        // Create a BoxLayout for the info panel (vertical layout)
        JPanel infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        infoPanel.setLayout(layout);
        infoPanel.setBackground(new Color(30, 30, 30)); // Match background color

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
        portPanel.setBackground(new Color(30, 30, 30)); // Match background color
        portPanel.add(portLabel);
        portPanel.add(portText);

        return portPanel;
    }

    private JPanel getIPLabel() {
        // Display local IP address
        JLabel ipLabel = new GameLabel("Your IP Address:  " + getLocalIPAddress(), SwingConstants.CENTER);

        // Panel to hold the IP label
        JPanel ipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the label to the left
        ipPanel.setBackground(new Color(30, 30, 30)); // Match background color
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