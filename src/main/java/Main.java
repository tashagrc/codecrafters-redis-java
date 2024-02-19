import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  // bikin 5 thread
  private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
       ServerSocket serverSocket;
       Socket clientSocket = null;
       int port = 6379;
       try {
          serverSocket = new ServerSocket(port);
          serverSocket.setReuseAddress(true);
          // setiap command dimasukin ke thread utk dihandle oleh ClientHandler
          while(true) {
            clientSocket = serverSocket.accept();
            executorService.submit(new ClientHandler(clientSocket));
          }
       } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
       } finally {
         try {
           if (clientSocket != null) {
             clientSocket.close();
           }
         } catch (IOException e) {
           System.out.println("IOException: " + e.getMessage());
         }
       }
  }
}
