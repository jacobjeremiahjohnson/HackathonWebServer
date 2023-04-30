import java.io.File;
import java.io.Serializable;

public class SerializablePhoto implements Serializable {
    private File image;
    private int id;
    private String name;
    private String base64;
    private String latitude;
    private String longitude;
    private String description;

    public SerializablePhoto(int id, String name, String latitude, String longitude, String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Photo convert(){
        Photo photo = new Photo(id, name, latitude, longitude, description);
        return photo;
    }
}
