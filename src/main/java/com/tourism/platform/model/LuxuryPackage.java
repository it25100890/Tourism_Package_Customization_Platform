package com.tourism.platform.model;

public class LuxuryPackage extends TourPackage {
    public LuxuryPackage() {
        this.type = "Luxury";
    }

    public LuxuryPackage(String id, String title, String destination, double price, String category) {
        super(id, title, destination, price, category);
        this.type = "Luxury";
    }

    @Override
    public double calculateDiscount(int participants) {
        if (participants >= 5) {
            return this.price * 0.05;
        }
        return 0.0;
    }

}
