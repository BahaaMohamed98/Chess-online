package game;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import networking.Communicator;
import ui.components.GameCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.sound.sampled.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class chessBoard extends JPanel {
    private final JButton[][] gameCells = new GameCell[8][8];
    private final HashMap<JButton, Piece> pieceMap = new HashMap<>();
    private Board board;
    private Piece selectedPiece = null;
    private List<Move> selectedPieceLegalMoves;
    private int selectedRow = -1, selectedCol = -1;
    private Side sideToPlay = Side.WHITE;
    private Side playerSide = null;

    private static final Color color_white = new Color(Color.white.getRGB());
    private static final Color color_black = new Color(129, 182, 76);

    private Communicator communicator = null;

    public chessBoard() {
        setLayout(new GridLayout(8, 8));

        this.board = new Board();
        this.board.setSideToMove(Side.WHITE);

        initializeCells();
        initializePieces();

        setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        int size = Math.min(getParent().getWidth(), getParent().getHeight());
        return new Dimension(size, size);
    }

    private void initializeCells() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton cell = new GameCell(getDefaultColor(row, col));

                cell.addActionListener(new ChessActionListener(row, col));

                gameCells[row][col] = cell;
                add(cell);
            }
        }
    }

    public void setPlayerSide(Side playerSide) {
        this.playerSide = playerSide;
    }


    private class ChessActionListener implements ActionListener {
        private final int row, col;

        public ChessActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedCell = gameCells[row][col];

            // select the clicked piece if no piece was selected
            if (selectedPiece == null && pieceMap.containsKey(clickedCell)) {
                selectPiece(clickedCell);
            } else if (selectedPiece != null) {
                if (selectedRow == row && selectedCol == col) { // if the same piece is selected again, deselect it
                    deselectPiece();
                } else if (sameTeam(getPiece(selectedRow, selectedCol), getPiece(row, col))) {
                    // if the second piece is from the same team, discard the first
                    deselectPiece(); // discard the first piece
                    selectPiece(clickedCell); // select the second piece
                } else {
                    handleMove(); // make the move
                }
            }
        }

        private void selectPiece(JButton clickedCell) {
            if ((playerSide != null) && !playerSide.equals(sideToPlay)) {
                return;
            }

            // check for whose turn
            if (!pieceMap.get(clickedCell).getPieceSide().equals(sideToPlay)) {
                return;
            }

            // Select a piece
            selectedPiece = pieceMap.get(clickedCell);
            selectedRow = row;
            selectedCol = col;
            clickedCell.setBackground(Color.YELLOW);

            selectedPieceLegalMoves = board.legalMoves().stream()
                    .filter(item -> item.getFrom().equals(getSquare(selectedRow, selectedCol)))
                    .toList();

            for (final Move move : selectedPieceLegalMoves) {
                getCell(move.getTo()).setBackground(new Color(173, 216, 230));
            }
        }

        private void handleMove() {
            // Try to move the piece
            if (isValidMove(selectedRow, selectedCol, row, col)) {
                // Move the piece
                makeMove(selectedRow, selectedCol, row, col, true);
            } else {
                playSound("illegal");
            }

            // Deselect the piece
            deselectPiece();
        }

        private boolean sameTeam(Piece first, Piece second) {
            return first.getPieceSide().equals(second.getPieceSide());
        }
    }


    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    private void initializePieces() {
        for (int row = 0; row < 8; ++row) {
            for (int col = 0; col < 8; ++col) {
                Piece piece = getPiece(row, col);

                if (!piece.equals(Piece.NONE)) {
                    setPiece(gameCells[row][col], getPiece(row, col));
                }
            }
        }
    }

    private JButton getCell(Square square) {
        int row = getRow(square);
        int col = getCol(square);

        return gameCells[row][col];
    }

    private int getRow(String position) {
        assert position.length() == 2;

        return 8 - (position.charAt(1) - '0' - 1) - 1;
    }

    private int getRow(Square square) {
        return 8 - square.getRank().ordinal() - 1;
    }

    private int getCol(String position) {
        assert position.length() == 2;

        return position.charAt(0) - 'a';
    }

    private int getCol(Square square) {
        return square.getFile().ordinal();
    }

    private void setPiece(JButton button, Piece piece) {
        // Associate the button with the piece name
        pieceMap.put(button, piece);
        button.setIcon(loadPieceImage(piece));
    }

    private ImageIcon loadPieceImage(Piece piece) {
        String filePath = "/images/" + piece.name() + ".png";
        var iconURL = getClass().getResource(filePath);

        if (iconURL == null) {
            System.err.println("Image not found: " + filePath);
            return null;
        }

        ImageIcon icon = new ImageIcon(iconURL);

        int size = 70;
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
            gameCells[selectedRow][selectedCol].setBackground(getDefaultColor(selectedRow, selectedCol));

            for (final Move move : selectedPieceLegalMoves) {
                final Square toSquare = move.getTo();

                int row = getRow(toSquare);
                int col = getCol(toSquare);

                gameCells[row][col].setBackground(getDefaultColor(row, col));

            }
        }

        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
        selectedPieceLegalMoves = null;
    }

    private void makeMove(int fromRow, int fromCol, int toRow, int toCol, boolean sendMove) {
        JButton fromCell = gameCells[fromRow][fromCol], toCell = gameCells[toRow][toCol];

        if (fromCell == null || toCell == null || !pieceMap.containsKey(fromCell)) {
            return;
        }

        board.doMove(getMove(fromRow, fromCol, toRow, toCol));

        doPromotion(fromRow, fromCol, toRow, toCol);

        pieceMap.remove(fromCell); // Clear the old cell
        fromCell.setIcon(null);

        if (board.isKingAttacked()) {
            playSound("check");
        } else if (pieceMap.containsKey(toCell)) {
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

    private void doPromotion(int fromRow, int fromCol, int toRow, int toCol) {
        Square toSquare = getSquare(toRow, toCol);
        Piece newPiece = board.getPiece(toSquare);

        if (pieceMap.get(gameCells[fromRow][fromCol]).getPieceType().equals(PieceType.PAWN) && !newPiece.getPieceType().equals(PieceType.PAWN)) {
            selectedPiece = newPiece;
        }
    }

    private Square getSquare(int row, int col) {
        int linearIndex = (8 - row - 1) * 8 + col;
        return Square.squareAt(linearIndex);
    }

    private Piece getPiece(int row, int col) {
        return board.getPiece(getSquare(row, col));
    }

    private Move getMove(int fromRow, int fromCol, int toRow, int toCol) {
        Square fromSquare = getSquare(fromRow, fromCol), toSquare = getSquare(toRow, toCol);
        Piece promotion = getPromotionPiece(fromSquare, toSquare);

        return new Move(fromSquare, toSquare, promotion);
    }

    private Piece getPromotionPiece(Square fromSquare, Square toSquare) {
        int toRow = getRow(toSquare);

        if (toRow == 0 && board.getPiece(fromSquare).equals(Piece.WHITE_PAWN)) {
            return Piece.WHITE_QUEEN;
        } else if (toRow == 7 && board.getPiece(fromSquare).equals(Piece.BLACK_PAWN)) {
            return Piece.BLACK_QUEEN;
        }

        return Piece.NONE;
    }

    private String getMoveNotation(int fromRow, int fromCol, int toRow, int toCol) {
        return new Move(getSquare(fromRow, fromCol), getSquare(toRow, toCol)).toString();
    }

    private void toggleTurn() {
        this.sideToPlay = sideToPlay == Side.WHITE ? Side.BLACK : Side.WHITE;
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        Move move = getMove(fromRow, fromCol, toRow, toCol);

        return board.legalMoves().contains(move);
    }

    public void makeMove(String move) {
        if (move.equals("reset")) {
            reset(false);
            return;
        }


        assert move.length() == 4;
        String[] mv = {move.substring(0, 2), move.substring(2)};

        int fromRow = getRow(mv[0]), fromCol = getCol(mv[0]),
                toRow = getRow(mv[1]), toCol = getCol(mv[1]);

        JButton fromCell = gameCells[fromRow][fromCol];

        selectedPiece = pieceMap.get(fromCell);
        makeMove(fromRow, fromCol, toRow, toCol, false);
        selectedPiece = null;
    }

    public void reset(boolean send) {
        if (send && communicator != null) {
            communicator.sendMove("reset");
        }

        pieceMap.clear();
        this.sideToPlay = Side.WHITE;
        this.board = new Board();

        for (JButton[] row : gameCells) {
            for (JButton button : row) {
                button.setIcon(null);
            }
        }

        deselectPiece();
        initializePieces();
    }
}
