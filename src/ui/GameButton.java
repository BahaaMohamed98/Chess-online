package ui;

import javax.swing.*;

public class GameButton extends JButton {
    public GameButton() {
        init();
    }

    public GameButton(String text) {
        init();
        setText(text);
    }

    public void init() {
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(true);
        setBorderPainted(false);
    }
}
