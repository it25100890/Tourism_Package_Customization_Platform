package com.tourism.platform.model;

import java.io.Serializable;

public class Customization implements Serializable {
    private String customizationId;
    private Long bookingId;
    private String mealPlan;
    private String roomType;
    private String vehicleType;
    private String extraActivities;
    private boolean needsGuide;
    private boolean needsAirportPickup;
    private boolean needsDriver;
    private double additionalCost;

    public Customization() {}

    public Customization(String customizationId, Long bookingId, String mealPlan, String roomType, String vehicleType, String extraActivities, boolean needsGuide, boolean needsAirportPickup, boolean needsDriver, double additionalCost) {
        this.customizationId = customizationId;
        this.bookingId = bookingId;
        this.mealPlan = mealPlan;
        this.roomType = roomType;
        this.vehicleType = vehicleType;
        this.extraActivities = extraActivities;
        this.needsGuide = needsGuide;
        this.needsAirportPickup = needsAirportPickup;
        this.needsDriver = needsDriver;
        this.additionalCost = additionalCost;
    }

    public String getCustomizationId() { return customizationId; }
    public void setCustomizationId(String customizationId) { this.customizationId = customizationId; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public String getMealPlan() { return mealPlan; }
    public void setMealPlan(String mealPlan) { this.mealPlan = mealPlan; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getExtraActivities() { return extraActivities; }
    public void setExtraActivities(String extraActivities) { this.extraActivities = extraActivities; }
    public boolean isNeedsGuide() { return needsGuide; }
    public void setNeedsGuide(boolean needsGuide) { this.needsGuide = needsGuide; }
    public boolean isNeedsAirportPickup() { return needsAirportPickup; }
    public void setNeedsAirportPickup(boolean needsAirportPickup) { this.needsAirportPickup = needsAirportPickup; }
    public boolean isNeedsDriver() { return needsDriver; }
    public void setNeedsDriver(boolean needsDriver) { this.needsDriver = needsDriver; }
    public double getAdditionalCost() { return additionalCost; }
    public void setAdditionalCost(double additionalCost) { this.additionalCost = additionalCost; }

    @Override
    public String toString() {
        return customizationId + "|" + bookingId + "|" + mealPlan + "|" + roomType + "|" + vehicleType + "|" + extraActivities + "|" + needsGuide + "|" + needsAirportPickup + "|" + needsDriver + "|" + additionalCost;
    }
}
