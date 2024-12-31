package networking;

import game.ChessBoard;

import java.io.*;
import java.net.Socket;

public class GameClient extends Communicator {
    private Socket socket;

    public GameClient(ChessBoard chessBoard) {
        super.chessBoard = chessBoard;
    }

    // Start the connection to the server
    public void connect(String serverAddress, int port) {
        isRunning = true;

        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to the server: " + e.getMessage());
        }

        System.out.println("Connected to server at " + serverAddress + ":" + port);

        // Start a thread to receive moves from the server
        super.handleConnection();
    }

    @Override
    public void close() {
        super.close();

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Failed to close resources: " + e.getMessage());
        }
    }
}
