package game;

import networking.Communicator;
import ui.GameButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.sound.sampled.*;

import java.io.IOException;
import java.util.Objects;

public class chessBoard extends JPanel {
    private final JButton[][] board = new GameButton[8][8];
    private final HashMap<JButton, String> pieceMap = new HashMap<>();
    private String selectedPiece = null;
    private int selectedRow = -1, selectedCol = -1;
    private boolean whiteTurn = true;

    private static final Color color_white = new Color(Color.white.getRGB());
    private static final Color color_black = new Color(129, 182, 76);

    private Communicator communicator = null;

    public chessBoard() {
        setSize(600, 600);
        setLayout(new GridLayout(8, 8));

        initializeBoard();
        initializePieces();

        setVisible(true);
    }

    private void initializeBoard() {
        boolean isWhite = true; // Alternate colors for the chessboard

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton cell = new GameButton();

                cell.setBackground(isWhite ? color_white : color_black);
                cell.addActionListener(new ChessActionListener(row, col));

                board[row][col] = cell;
                add(cell);

                isWhite = !isWhite; // Toggle color
            }
            isWhite = !isWhite; // Alternate at the end of each row
        }
    }

    private class ChessActionListener implements ActionListener {
        private final int row, col;

        public ChessActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedCell = board[row][col];

            if (selectedPiece == null && pieceMap.containsKey(clickedCell)) { // select piece if no piece was selected
                selectPiece(clickedCell);
            } else if (selectedPiece != null) {
                if (selectedRow == row && selectedCol == col) { // if the same piece is selected again deselect it
                    deselectPiece();
                } else if (sameTeam(board[selectedRow][selectedCol], board[row][col])) { // if the second piece is from the same team discard the first
                    deselectPiece(); // discard the first piece
                    actionPerformed(e); // select the second piece
                } else {
                    handleMove(); // make the move
                }
            }
        }

        private void selectPiece(JButton clickedCell) {
            // check for whose turn
            if ((pieceMap.get(clickedCell).startsWith("black") && whiteTurn) || (pieceMap.get(clickedCell).startsWith("white") && !whiteTurn)) {
                return;
            }

            // Select a piece
            selectedPiece = pieceMap.get(clickedCell);
            selectedRow = row;
            selectedCol = col;
            clickedCell.setBackground(Color.YELLOW);
        }

        private void handleMove() {
            // Try to move the piece
            if (isValidMove(selectedPiece, selectedRow, selectedCol, row, col)) {
                // Move the piece
                makeMove(selectedRow, selectedCol, row, col, true);
            } else {
                playSound("illegal");
            }

            // Deselect the piece
            deselectPiece();
        }

        private boolean sameTeam(JButton first, JButton second) {
            String s = pieceMap.get(second);

            if (s == null) {
                return false;
            }

            return (pieceMap.get(first).startsWith("white") && s.startsWith("white")) ||
                    (pieceMap.get(first).startsWith("black") && s.startsWith("black"));
        }
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    private void initializePieces(String color) {
        for (int j = 0; j < 8; ++j) {
            setPiece(getCell(((char) ('a' + j)) + (color.equals("white") ? "2" : "7")), "pawn", color);
        }

        int rank = color.equals("white") ? 1 : 8;

        setPiece(getCell("a" + (char) ('0' + rank)), "rook", color);
        setPiece(getCell("h" + (char) ('0' + rank)), "rook", color);

        setPiece(getCell("b" + (char) ('0' + rank)), "knight", color);
        setPiece(getCell("g" + (char) ('0' + rank)), "knight", color);

        setPiece(getCell("c" + (char) ('0' + rank)), "bishop", color);
        setPiece(getCell("f" + (char) ('0' + rank)), "bishop", color);


        setPiece(getCell("d" + (char) ('0' + rank)), "queen", color);

        setPiece(getCell("e" + (char) ('0' + rank)), "king", color);
    }

    private void initializePieces() {
        initializePieces("white");
        initializePieces("black");
    }

    private JButton getCell(String position) {
        int col = getCol(position);
        int row = getRow(position);

        return board[row][col];
    }

    private int getRow(String position) {
        assert position.length() == 2;

        return 8 - (position.charAt(1) - '0' - 1) - 1;
    }

    private int getCol(String position) {
        assert position.length() == 2;

        return position.charAt(0) - 'a';
    }

    private void setPiece(JButton button, String pieceName, String color) {
        // Associate the button with the piece name
        String name = color.isEmpty() ? pieceName : color + "-" + pieceName;
        pieceMap.put(button, name);
        button.setIcon(loadPieceImage(name));
    }

    private void setPiece(JButton button, String pieceName) {
        setPiece(button, pieceName, "");
    }

    private ImageIcon loadPieceImage(String pieceName) {
        String filePath = "/images/" + pieceName + ".png";
        var iconURL = getClass().getResource(filePath);

        if (iconURL == null) {
            System.err.println("Image not found: " + filePath);
            return null;
        }

        ImageIcon icon = new ImageIcon(iconURL);

        int size = getWidth() / 8;
        Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void playSound(String soundFile) {
        new Thread(() -> {
            try {
                String path = "/sounds/" + soundFile + ".wav";
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(path)));
                var clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }

    private Color getDefaultColor(int row, int col) {
        return (row + col) % 2 == 0 ? color_white : color_black;
    }

    private void deselectPiece() {
        if (selectedCol != -1 && selectedRow != -1) {
            board[selectedRow][selectedCol].setBackground(getDefaultColor(selectedRow, selectedCol));
        }

        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
    }

    private void makeMove(int fromRow, int fromCol, int toRow, int toCol, boolean sendMove) {
        JButton fromCell = board[fromRow][fromCol], toCell = board[toRow][toCol];

        if (fromCell == null || toCell == null || !pieceMap.containsKey(fromCell)) {
            return;
        }

        pieceMap.remove(fromCell); // Clear the old cell
        fromCell.setIcon(null);

        if (pieceMap.containsKey(toCell)) {
            playSound("capture");
        } else {
            playSound("move");
        }

        setPiece(toCell, selectedPiece); // Set the piece in the new cell

        if (sendMove && communicator != null) {
            communicator.sendMove(getMoveNotation(fromRow, fromCol, toRow, toCol));
        }

        toggleTurn();
    }

    private String getMoveNotation(int fromRow, int fromCol, int toRow, int toCol) {
        // Convert column index to letter (a-h)
        String from = "" + (char) ('a' + fromCol);
        // Convert row index to 1-based notation (1-8), so add 1 to fromRow and toRow
        from += (8 - fromRow);  // The row 0 corresponds to rank 8, row 7 to rank 1

        String to = "" + (char) ('a' + toCol);
        to += (8 - toRow);  // Convert to 1-based notation

        return from + "-" + to;
    }

    private void toggleTurn() {
        whiteTurn = !whiteTurn;
    }

    private boolean isValidMove(String ignoredPiece, int ignoredFromRow, int ignoredFromCol, int ignoredToRow, int ignoredToCol) {
        return true;
    }

    public void updateBoard(String move) {
        if (move.equals("reset")) {
            reset(false);
            return;
        }

        String[] mv = move.split("-");
        assert mv.length == 2;

        int fromRow = getRow(mv[0]), fromCol = getCol(mv[0]),
                toRow = getRow(mv[1]), toCol = getCol(mv[1]);

        JButton fromCell = board[fromRow][fromCol];

        selectedPiece = pieceMap.get(fromCell);
        makeMove(fromRow, fromCol, toRow, toCol, false);
        selectedPiece = null;
    }

    public void reset(boolean send) {
        if (send && communicator != null) {
            communicator.sendMove("reset");
        }

        pieceMap.clear();
        this.whiteTurn = true;

        for (JButton[] row : board) {
            for (JButton button : row) {
                button.setIcon(null);
            }
        }

        deselectPiece();
        initializePieces();
    }
}
