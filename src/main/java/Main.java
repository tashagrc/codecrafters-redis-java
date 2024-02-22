import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

  static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

  public static void main(String[] args){
    
       ServerSocket serverSocket = null;
       Socket clientSocket = null;
       int port = 6379;
       try {
          serverSocket = new ServerSocket(port);
          serverSocket.setReuseAddress(true);
          // setiap command dimasukin ke thread utk dihandle oleh ClientHandler
          while(true) {
            clientSocket = serverSocket.accept();
            Thread newThread = new Thread(new ClientHandler(clientSocket, cache));
            newThread.start();
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
