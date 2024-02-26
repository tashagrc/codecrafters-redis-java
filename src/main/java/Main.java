import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException{
    var portNumber = initializePort(args);
    var serverSocket = RedisConnectionUtil.createRedisServerSocket(portNumber);
    while(true) {
      var clientSocket = serverSocket.accept();
      var redisClientThread = new RedisConnectionThread(clientSocket);
      redisClientThread.start();
    }

       
  }

  public static int initializePort(String[] args) {
    parseConfig(args);
    var dir = RedisRepository.configGet("dir");
    var dbFileName = RedisRepository.configGet("dbfilename");
    var port = RedisRepository.configGet("port");

    if(dir != null && dbFileName != null) {
      try {
        var data = RdbUtil.openRdbFile(dir, dbFileName);
        var builder = new RdbBuilder().bytes(data);
        var rdb = builder.build();

        if(rdb != null) {
          rdb.init();
        }
      } catch(Exception e) {
        System.out.println("rdb read failed");
      }
    }

    if(port != null) {
      try {
        var portNumber = Integer.parseInt(port);
        return portNumber;
      } catch(Exception e) {
        System.out.println("setting custom port number failed");
      }
    }

    return Constant.DEFAULT_REDIS_PORT;
  }

  public static void parseConfig(String[] args) {
    for(int i = 0; i < args.length; i+= 2) {
      for(int i = 0; i < args.length; i+= 2) {
        var key = args[i].substring(2);
        var value = args[i+1];
        RedisRepository.configSet(key, value);
      }
    }
  }
}
