package model;

public class TourismPackage {
    private String packageId;
    private String packageName;
    private String description;
    private String location;
    private double price;
    private int duration;
    private boolean availability;

    //Create a parameterized constructor
    public TourismPackage(String packageId, String packageName, String description, String location, double price, int duration, boolean availability) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.location = location;
        this.price = price;
        this.duration = duration;
        this.availability = availability;
    }
    //get and set packageId,packageName,description,location,price,duration and availability.

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}


