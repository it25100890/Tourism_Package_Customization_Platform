package com.tourism.platform.model;

public class CashPayment extends Payment {
    private String receiptNumber;

    public CashPayment() {}

    public CashPayment(String paymentId, String bookingId, double amount, String paymentDate, String receiptNumber) {
        super(paymentId, bookingId, amount, paymentDate);
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String processPayment() {
        return "Processed Cash Payment for amount: $" + amount + " with receipt: " + receiptNumber;
    }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    @Override
    public String toString() {
        return super.toString() + "|" + receiptNumber;
    }
}
