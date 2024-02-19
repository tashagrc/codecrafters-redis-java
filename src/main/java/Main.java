import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    //  Uncomment this block to pass the first stage
       ServerSocket serverSocket;
       Socket clientSocket = null;
       String serverResponse = "+PONG\r\n";
       String clientMessage;
       int port = 6379;
       try {
         serverSocket = new ServerSocket(port);
         serverSocket.setReuseAddress(true);
         // Wait for connection from client.
         clientSocket = serverSocket.accept();

         // baca tulis input output dr socket
         BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);

         // untuk setiap line yg disend client, server respon pong
         while((clientMessage = input.readLine()) != null) {
          System.out.println("Command received: " + clientMessage);
          if(clientMessage.equalsIgnoreCase("ping")) {
            outWriter.print(serverResponse);
            outWriter.flush();
          }
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
