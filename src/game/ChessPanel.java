package game;

import ui.ChessApp;
import ui.GameButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChessPanel extends JPanel {
    chessBoard chessBoard;

    public ChessPanel(chessBoard chessBoard) {
        setLayout(new BorderLayout());

        // The main chess board
        this.chessBoard = chessBoard;
        add(chessBoard);

        // Footer Panel for info and buttons
        JPanel footerPanel = getFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());

        JLabel playerLabel = new JLabel("Player: BahaaMohamed98", SwingConstants.CENTER);
        footerPanel.add(playerLabel, BorderLayout.NORTH);

        // Create the menu button and wrap it with a JPanel for padding
        JButton menuButton = new GameButton("Menu");
        menuButton.addActionListener(_ -> ChessApp.show(ChessApp.mainPanel, "Menu"));

        JPanel menuButtonPanel = new JPanel(new BorderLayout());
        menuButtonPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding
        menuButtonPanel.add(menuButton, BorderLayout.CENTER);
        footerPanel.add(menuButtonPanel, BorderLayout.WEST);

        // Create the exit button and wrap it with a JPanel for padding
        JButton exit = new GameButton("Exit");
        exit.addActionListener(_ -> System.exit(0));

        JPanel exitButtonPanel = new JPanel(new BorderLayout());
        exitButtonPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding
        exitButtonPanel.add(exit, BorderLayout.CENTER);
        footerPanel.add(exitButtonPanel, BorderLayout.EAST);

        JButton test = new GameButton("Restart");
        test.addActionListener(_ -> chessBoard.reset(true));
        footerPanel.add(test, BorderLayout.CENTER);

        return footerPanel;
    }
}
