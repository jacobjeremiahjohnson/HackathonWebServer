import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("GET");
        String[] path = exchange.getRequestURI().getPath().split("/");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if (path.length != 3) {
            sendResponse(exchange, 400, "Bad Request"); //issue jAWN daddy
            System.out.println(Arrays.toString(path));
            return;
        }
        String id;
        id = path[2];
        System.out.println(App.photos);
        Photo photo = App.photos.get(Integer.parseInt(id));
        System.out.println(id);


        if (photo == null) {
            sendResponse(exchange, 404, "Not Found");
        } else {
            sendResponse(exchange, 200, new JSONObject(photo).toString());
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(message.getBytes());
        responseBody.close();
    }

}



