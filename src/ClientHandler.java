import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Set<ClientHandler> clients;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); // come back
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void  sendMessage(String msg){
        writer.println(msg);
    }

    @Override
    public void run() {
        try {
            String name = reader.readLine();
            broadCast(name + ": new joine chat  ");
            String msg ;

            while ((msg = reader.readLine()) !=null){
                broadCast( name + " :" + msg );
            }
        } catch (IOException e) {
            System.out.println(  " client disconneted ");
        }finally {
            try {
                socket.close();
                clients.remove(this);
                broadCast("A user has left the chat ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void broadCast(String msg){
        for (ClientHandler client : clients){
            client.sendMessage(msg);
        }
    }
}
