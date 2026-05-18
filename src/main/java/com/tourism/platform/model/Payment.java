package com.tourism.platform.model;

import java.io.Serializable;

public abstract class Payment implements Serializable {
    protected String paymentId;
    protected String bookingId;
    protected double amount;
    protected String paymentDate;

    public Payment() {}

    public Payment(String paymentId, String bookingId, double amount, String paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public abstract String processPayment();

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return paymentId + "|" + bookingId + "|" + amount + "|" + paymentDate + "|" + getClass().getSimpleName();
    }
}
