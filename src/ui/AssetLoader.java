package ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AssetLoader {
    private static final HashMap<String, ImageIcon> icons = new HashMap<>();
    private static final HashMap<String, Clip> sounds = new HashMap<>();

    public static ImageIcon getIcon(String piece, int width, int height) {
        if (icons.containsKey(piece)) {
            return icons.get(piece);
        }

        ImageIcon icon = loadPieceIcon(piece, width, height);

        if (icon != null) {
            icons.put(piece, icon);
        }

        return icon;
    }

    private static ImageIcon loadPieceIcon(String piece, int width, int height) {
        String filePath = "/images/" + piece + ".png";
        var iconURL = AssetLoader.class.getResource(filePath);

        if (iconURL == null) {
            System.err.println("Image not found: " + filePath);
            return null;
        }

        ImageIcon icon = new ImageIcon(iconURL);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static Clip getSound(String soundName) {
        if (sounds.containsKey(soundName)) {
            Clip clip = sounds.get(soundName);
            clip.setFramePosition(0);
            return clip;
        }

        Clip clip = loadSound(soundName);

        if (clip != null) {
            sounds.put(soundName, clip);
        }

        return clip;
    }

    private static Clip loadSound(String soundName) {
        try {
            String path = "/sounds/" + soundName + ".wav";
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(AssetLoader.class.getResource(path)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + e.getMessage());
            return null;
        }
    }
}