package networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameHost extends Communicator {
    private ServerSocket serverSocket;

    public GameHost(game.chessBoard chessBoard) {
        super.chessBoard = chessBoard;
    }

    public void start(int port) {
        isRunning = true;

        try {
            serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initiate server socket");
        }

        System.out.println("started with port: " + port);

        super.handleConnection();
    }

    @Override
    public void close() {
        super.close();

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Failed to close resources: " + e.getMessage());
        }
    }
}
