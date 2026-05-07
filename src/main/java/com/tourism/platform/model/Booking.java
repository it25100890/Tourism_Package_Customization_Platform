package com.tourism.platform.model;

public class Booking {
    private String bookingId;
    private String userId;
    private String packageId;
    private String customizationId;
    private String travelDate;
    private int participants;
    private double totalAmount;
    private String status;
    private String createdAt;

    public Booking() {}

    public Booking(String bookingId, String userId, String packageId,
                   String customizationId, String travelDate,
                   int participants, double totalAmount, String status,
                   String createdAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.packageId = packageId;
        this.customizationId = customizationId;
        this.travelDate = travelDate;
        this.participants = participants;
        this.totalAmount = totalAmount;
        this.status = "Pending";
        this.createdAt = createdAt;
    }
}
