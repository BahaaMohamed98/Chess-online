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
    static public JPanel mainPanel;
    static public CardLayout layout;
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

    public static void show(JPanel panel, String name) {
        layout.show(panel, name);
    }

    public static void main() {
        SwingUtilities.invokeLater(ChessApp::new);
    }
}
