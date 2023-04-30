import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import org.json.JSONStringer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {

        Photo photo;

        try {
            Headers headers = exchange.getResponseHeaders();
            headers.set("Access-Control-Allow-Origin", "*");
            headers.set("Access-Control-Allow-Methods", "POST");
            headers.set("Access-Control-Allow-Headers", "Content-Type");

            Headers requestHeaders = exchange.getRequestHeaders();
            System.out.println(requestHeaders.get("Content-type"));

            if(requestHeaders.get("Content-type") != null) {
                System.out.println(requestHeaders.get("Content-type").get(0));
            }
            if(requestHeaders.get("Content-type") != null && requestHeaders.get("Content-type").get(0).equals("application/json")){
                System.out.println("Json upload");

                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String postData = br.readLine();

                // Process the post data here
                String response = "POST request received: " + postData;
                // Send response

                if (postData != null) {

                    postData = postData.replaceAll("\r?\n", "");
                    JsonReader reader = Json.createReader(new StringReader(postData));
                    JsonObject jsonObject = reader.readObject();
                    reader.close();

                    String name = jsonObject.getString("name");
                    String latitude = jsonObject.getString("latitude");
                    String longitude = jsonObject.getString("longitude");
                    String description = jsonObject.getString("description");

                    photo = new Photo(App.id, name, latitude, longitude, description);

                    App.photos.add(photo.getId(), photo);

                    Database.writeFile();
                    Database.readFile();
                }

            } else if (requestHeaders.get("Content-type") != null && requestHeaders.get("Content-type").get(0).equals("image/jpeg")){
                System.out.println("File uplaod");

                InputStream inputStream = exchange.getRequestBody();
                byte[] buffer = new byte[1024];
                int bytesRead;
                OutputStream outputStream = null;
                try {
                    App.id++;
                    int filename = App.id + 1;
                    File outputFile = new File("src/" + filename + ".jpg");
                    outputStream = new FileOutputStream(outputFile);
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    exchange.getResponseBody().close();
                    System.out.println("File uploaded successfully");

                    App.pictures.add(outputFile);

                } catch (IOException e) {
                    exchange.sendResponseHeaders(500, -1);
                    exchange.getResponseBody().close();
                    System.out.println("File upload failed");
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    return;
                }
            }

            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String postData = br.readLine();

            // Process the post data here
            String response = "POST request received: " + postData;
            // Send response

            if (postData != null) {

                postData = postData.replaceAll("\r?\n", "");
                JsonReader reader = Json.createReader(new StringReader(postData));
                JsonObject jsonObject = reader.readObject();
                reader.close();

                String name = jsonObject.getString("name");
                String base64 = jsonObject.getString("base64");
                String latitude = jsonObject.getString("latitude");
                String longitude = jsonObject.getString("longitude");
                String description = jsonObject.getString("description");

                //Photo photo = new Photo(nextId++, name, base64, latitude, longitude);

                //App.photos.put(photo.getId(), photo);
            }

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
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

    private String readRequestBody(InputStream requestBody) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = requestBody.read()) != -1) {
            sb.append((char) b);
        }
        return sb.toString();
    }
}
