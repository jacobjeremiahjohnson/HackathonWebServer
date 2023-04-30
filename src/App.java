import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

    public static Map<Integer, Photo> photos = new HashMap<>();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/GET/", new GetHandler());
        server.createContext("/POST/", new PostHandler());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor); // creates a default executor
        server.start();
    }
}

