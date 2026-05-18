package com.tourism.platform.model;

public class StandardPackage extends TourPackage {
    public StandardPackage() {
        this.type = "Standard";
    }

    public StandardPackage(String id, String title, String destination, double price, String category) {
        super(id, title, destination, price, category);
        this.type = "Standard";
    }

    @Override
    public double calculateDiscount(int participants) {
        if (participants >= 10) {
            return this.price * 0.10;
        }
        return 0.0;
    }
}


