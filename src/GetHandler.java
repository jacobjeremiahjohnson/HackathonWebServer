import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GetHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            System.out.println("GET");
            String[] path = exchange.getRequestURI().getPath().split("/");
            Headers headers = exchange.getResponseHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "GET");
            headers.set("Access-Control-Allow-Headers", "Content-Type");
            if (path.length != 4) {
                sendResponse(exchange, 400, "Bad Request"); //issue jAWN daddy
                System.out.println(Arrays.toString(path));
                return;
            }
            String id;
            id = path[2];

            if (path[3].equals("json")) {
                System.out.println(App.photos);
                Photo photo = App.photos.get(Integer.parseInt(id));
                System.out.println(photo.getName());
                System.out.println(photo.getDescription());
                System.out.println(id);

                if (photo == null) {
                    sendResponse(exchange, 404, "Not Found");
                } else {
                    sendResponse(exchange, 200, new JSONObject(photo).toString());
                }
            } else if (path[3].equals("image")) {
                System.out.println("image");
                File file = new File("src/" + (Integer.parseInt(id) + 1) + ".jpg");

                exchange.sendResponseHeaders(200, file.length());
                OutputStream outputStream = exchange.getResponseBody();
                FileInputStream inputStream = new FileInputStream(file);

                byte[] buffer = new byte[4096];
                int count;
                while ((count = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                inputStream.close();
                outputStream.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(message.getBytes());
        responseBody.close();
    }

}



