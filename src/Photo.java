public class Photo {
    private int id;
    private String name;
    private String base64;
    private String latitude;
    private String longitude;
    private String description;

    public Photo(int id, String name, String base64, String latitude, String longitude, String description) {
        this.id = id;
        this.name = name;
        this.base64 = base64;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
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
        return description;
    }

    public void setName(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return this.name + " : " + this.id;
    }
}