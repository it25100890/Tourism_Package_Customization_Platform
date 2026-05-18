package com.tourism.platform.model;
import java.io.Serializable;
public class TourPackage {
    protected String id;
    protected String title;
    protected String destination;
    protected double price;
    protected String category;
    protected String type = "Standard";

    public TourPackage() {}
    public TourPackage(String id, String title, String destination, double price, String category) {
        this.id = id; this.title = title; this.destination = destination; this.price = price; this.category = category;
    }


    public double calculateDiscount(int participants) {
        return 0.0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override public String toString() {
        return id + "|" + title + "|" + destination + "|" + price + "|" + category + "|" + type;
    }
}
