import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FMServer {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    public static void main(String[] args){
        String port = args[0];
        new FMServer().startServer(port);
    }

    private void startServer(String portNum){
        System.out.println("Starting Family Map Server: HTTP Server");
        try{
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNum)), MAX_WAITING_CONNECTIONS);
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        registeringHandlers(server);
        server.start();
    }

    private void registeringHandlers(HttpServer server){
        System.out.println("Creating contexts");

        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new userRegister());
        server.createContext("/user/login", new userLogin());
        server.createContext("/clear", new clear());
        server.createContext("/fill/", new fill());
        server.createContext("/load", new load());
        server.createContext("/person/", new personID());
        server.createContext("/person", new people());
        server.createContext("/event/", new eventID());
        server.createContext("/event", new event());
    }
}

