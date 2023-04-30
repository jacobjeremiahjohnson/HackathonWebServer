import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;

public class Database {

    public static void writeFile() {
        clearFile();

        ArrayList<SerializablePhoto> sphoto = new ArrayList<>();
        for (Photo photo : App.photos) {
            sphoto.add(photo.convert());
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("photos.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(sphoto);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Output failure");
        }
    }

    public static ArrayList<Photo> readFile() throws IOException, ClassNotFoundException {

        ArrayList<SerializablePhoto> readList = new ArrayList<>();
        ArrayList<Photo> list = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream("photos.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        readList = (ArrayList<SerializablePhoto>) objectInputStream.readObject();

        for (SerializablePhoto sphoto : readList){
            list.add(sphoto.convert());
        }
        fileInputStream.close();
        objectInputStream.close();

        File dir = new File("src");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null){
            for (File child : directoryListing){
                if (child.getName().endsWith(".jpg")) {
                    App.pictures.add(child);
                }
            }
        }
        App.id = App.pictures.size();

        return list;
    }
    public static void clearFile() { //Utility method, clear text file
        try{
            FileWriter fw = new FileWriter("photos.txt", false);
            PrintWriter pw = new PrintWriter(fw, false);

            pw.flush();
            pw.close();
            fw.close();
        }catch(Exception exception){
            System.out.println("Exception have been caught");
        }
    }
}
