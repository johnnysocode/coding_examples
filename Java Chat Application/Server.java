import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/*
* JONATHAN SANTIAGO
* Server class that will create a Server Socket on a specified port.
* will continiously accept new connections and handles them using seperate threads
*/

public class Server {

    private final ServerSocket serverSocket;

    // Constructs a new server instance
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /*
     * Starts the server and continouslly waits for new client connections
     * each client will be handled in a seperate thread
     */
    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println(" A new Client has joined the server");

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

                }
            } catch (IOException e) {
                closeServerSocket();
            }    
    }

    // Closes the server socket 
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // the entry point of the application
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(2022);
        Server server = new Server(serverSocket);

        System.out.println("Server is open to Clients:");
        server.startServer();
    }
}