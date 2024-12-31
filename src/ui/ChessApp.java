package ui;

import game.ChessBoard;
import ui.panels.ChessGamePanel;
import ui.panels.ConnectGamePanel;
import ui.panels.HostGamePanel;
import ui.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessApp extends JFrame {
    private static JPanel mainPanel;
    private static CardLayout layout;
    private final ChessBoard chessBoard;

    public ChessApp() {
        setTitle("Chess");
        setSize(650, 800);
        setMinimumSize(new Dimension(600, 800));
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setAppImage();

        chessBoard = new ChessBoard();
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        addPanels();

        setVisible(true);
    }

    private void setAppImage() {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/gameIcon.png")));
        setIconImage(imageIcon.getImage());
    }

    private void addPanels() {
        mainPanel.add(new MenuPanel(), "Menu");
        mainPanel.add(new ChessGamePanel(chessBoard), "Game");
        mainPanel.add(new HostGamePanel(chessBoard), "Host");
        mainPanel.add(new ConnectGamePanel(chessBoard), "Connect");
        add(mainPanel);
    }

    public static void showMenu() {
        layout.show(mainPanel, "Menu");
    }

    public static void showGamePanel() {
        layout.show(mainPanel, "Game");
    }

    public static void showHostPanel() {
        layout.show(mainPanel, "Host");
    }

    public static void showConnectPanel() {
        layout.show(mainPanel, "Connect");
    }

    public static void exit() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessApp::new);
    }
}
