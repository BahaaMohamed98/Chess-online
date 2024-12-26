package ui;

import game.ChessPanel;
import game.chessBoard;

import javax.swing.*;
import java.awt.*;

public class ChessApp extends JFrame {
    static public JPanel mainPanel;
    static public CardLayout layout;

    public ChessApp() {
        setTitle("Chess");
        setSize(600, 650);
        // setResizable(false);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIconImage(new ImageIcon("/images/gameIcon.png").getImage());

        chessBoard chessBoard = new chessBoard();

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        JPanel menuPanel = getMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        JPanel gamePanel = new ChessPanel(chessBoard);
        mainPanel.add(gamePanel, "Game");

        JPanel hostGamePanel = new HostGamePanel(chessBoard);
        mainPanel.add(hostGamePanel, "Host");

        JPanel connectGamePanel = new ConnectGamePanel(chessBoard);
        mainPanel.add(connectGamePanel, "Connect");

        add(mainPanel);
        setVisible(true);
    }

    private static JPanel getMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(4, 1));

        JButton startButton = new GameButton("Start game");
        startButton.addActionListener(_ -> layout.show(mainPanel, "Game"));
        menuPanel.add(startButton);


        JButton hostGameButton = new GameButton("Host game");
        hostGameButton.addActionListener(_ -> layout.show(mainPanel, "Host"));
        menuPanel.add(hostGameButton);


        JButton connectGameButton = new GameButton("Connect to game");
        connectGameButton.addActionListener(_ -> layout.show(mainPanel, "Connect"));
        menuPanel.add(connectGameButton);


        JButton exitButton = new GameButton("Exit");
        exitButton.addActionListener(_ -> System.exit(0));
        menuPanel.add(exitButton);

        return menuPanel;
    }

    public static void show(JPanel panel, String name) {
        layout.show(panel, name);
    }

    public static void main(String[] args) {
        new ChessApp();
    }
}
