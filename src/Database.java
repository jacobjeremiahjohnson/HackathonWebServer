import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Database {
    public static ArrayList<Photo> photoArrayList = new ArrayList<>();
    public static ArrayList<String> locationArrayList = new ArrayList<>();

    public static void createFile (Photo photo) {
        File file = new File(photo.getName() + ".txt");
    }

    public static BufferedImage decodeFiletoImage(Photo photo) {
        BufferedImage image = null;
        byte[] data;
        try {
            data = Files.readAllBytes(Path.of(photo.getName() + ".txt"));
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            image = ImageIO.read(bis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
