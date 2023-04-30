import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

public class PostHandler implements HttpHandler {
    int nextId = 1;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("POST");
        InputStream requestBody = exchange.getRequestBody();
        JSONObject userJson = new JSONObject(readRequestBody(requestBody));
        String name = userJson.getString("name");
        String base64 = userJson.getString("base64");
        String latitude = userJson.getString("latitude");
        String longitude = userJson.getString("longitude");
        String description = userJson.getString("description");

        Photo photo = new Photo(nextId++, name, base64, latitude, longitude, description);

        App.photos.put(photo.getId(), photo);

        sendResponse(exchange, 201, new JSONObject(photo).toString());
}

    private void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.sendResponseHeaders(statusCode, message.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(message.getBytes());
        responseBody.close();
    }

    private String readRequestBody(InputStream requestBody) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = requestBody.read()) != -1) {
            sb.append((char) b);
        }
        return sb.toString();
    }
}
