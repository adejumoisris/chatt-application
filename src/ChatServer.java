import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) throws IOException {
        final int PORT = 1234;
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("server started on port " + PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected ");

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }
    }
}
