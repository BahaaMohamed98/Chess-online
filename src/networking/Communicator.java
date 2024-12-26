package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Communicator {
    protected game.chessBoard chessBoard;
    protected boolean isRunning;
    protected BufferedReader input;
    protected PrintWriter output = null;

    // Method to receive a move from the server
    protected String receiveMove() throws IOException {
        return input.readLine();
    }

    // Send a move to the server
    public void sendMove(String move)  {
        output.println(move);
        System.out.println("Sent move: " + move);
    }

    protected void handleConnection() {
        new Thread(() -> {
            while (isRunning) {
                try {
                    String move = receiveMove();
                    System.out.println("Received: " + move);

                    if (move == null) {
                        close();
                        return;
                    }

                    chessBoard.updateBoard(move);
                } catch (IOException e) {
                    throw new RuntimeException("Connection closed or network error");
                }
            }
        }).start();
    }

    // Close the connection
    public void close() {
        isRunning = false;

        try {
            if (input != null) input.close();
            if (output != null) output.close();
        } catch (IOException e) {
            System.out.println("Failed to close resources: " + e.getMessage());
        }
    }
}