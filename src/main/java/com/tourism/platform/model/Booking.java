package com.tourism.platform.model;

public class Booking {
    private Long bookingId;
    private String userId;
    private String packageId;
    private String customizationId;
    private String travelDate;
    private int participants;
    private double totalAmount;
    private String status;
    private String createdAt;
    private String receiptNumber;

    public Booking() {}

    public Booking(String userId, String packageId,
                   String customizationId, String travelDate,
                   int participants, double totalAmount, String status,
                   String createdAt, String receiptNumber) {
        this.userId = userId;
        this.packageId = packageId;
        this.customizationId = customizationId;
        this.travelDate = travelDate;
        this.participants = participants;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.receiptNumber = receiptNumber;
    }

    public Long getBookingId()
    { return bookingId; }

    public void setBookingId(Long bookingId)
    { this.bookingId = bookingId; }

    public String getUserId()
    { return userId; }

    public void setUserId(String userId)
    { this.userId = userId; }

    public String getPackageId()
    { return packageId; }

    public void setPackageId(String packageId)
    { this.packageId = packageId; }

    public String getCustomizationId()
    { return customizationId; }

    public void setCustomizationId(String customizationId)
    { this.customizationId = customizationId; }

    public String getTravelDate()
    { return travelDate; }

    public void setTravelDate(String travelDate)
    { this.travelDate = travelDate; }

    public int getParticipants()
    { return participants; }

    public void setParticipants(int participants)
    { this.participants = participants; }

    public double getTotalAmount()
    { return totalAmount; }

    public void setTotalAmount(double totalAmount)
    { this.totalAmount = totalAmount; }

    public String getStatus()
    { return status; }

    public void setStatus(String status)
    { this.status = status; }

    public String getCreatedAt()
    { return createdAt; }

    public void setCreatedAt(String createdAt)
    { this.createdAt = createdAt; }

    public String getReceiptNumber()
    { return receiptNumber; }

    public void setReceiptNumber(String receiptNumber)
    { this.receiptNumber = receiptNumber; }
}