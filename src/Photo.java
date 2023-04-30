import java.io.File;

public class Photo {
    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private String description;

    public Photo(int id, String name, String latitude, String longitude, String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + " : " + this.id;
    }


    public SerializablePhoto convert(){
        SerializablePhoto sphoto = new SerializablePhoto(id, name, latitude, longitude, description);
        return sphoto;
    }
}