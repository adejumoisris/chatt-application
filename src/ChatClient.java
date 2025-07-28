import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 1234;

        try (Socket socket = new Socket(HOST, PORT)){
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWritter = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your Name: ");
            String name = userInput.readLine();
            serverWritter.println(name);

            new Thread(()->{
                String serverMsg;
                try {




                          while ((serverMsg = serverReader.readLine()) !=null){
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconneted from server ");;
                }
            }).start();
            String msg ;
            while ((msg = userInput.readLine()) !=null){
                serverWritter.println(msg);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
