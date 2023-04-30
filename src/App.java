import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

    public static Map<Integer, Photo> photos = new HashMap<>();
    public static ArrayList<File> pictures = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        photos.put(0, new Photo(0, "Smith Hall", "39.68030203710849690424", "-75.75386590882111192968", "Smith Hall at UD. Main hall for computer science and host to Henhacks Hackathon 2023.", new File("1.jpg")));
        photos.put(1, new Photo(1, "Delaware City Refinery", "39.5809", "-75.6309", "Oil refinery in Delaware City. Beautiful at night. Large nitrogen polluter though. ", new File("2.jpg")));
        pictures.add(new File("2.jpg"));
        photos.put(2, new Photo(2, "Rehoboth Beach Bandstand", "38.71660416383861", "-75.07662814168542", "In the middle of the Rehoboth Boardwalk, the Rehoboth Beach Bandstand regularly hosts local musical groups to entertain beachgoers year-round.", new File("3.jpg")));
        pictures.add(new File("3.jpg"));

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/GET/", new GetHandler());
        server.createContext("/POST/", new PostHandler());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor); // creates a default executor
        server.start();
    }
}

