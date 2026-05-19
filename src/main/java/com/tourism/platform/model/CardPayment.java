package com.tourism.platform.model;

public class CardPayment extends Payment {
    private String cardNumberMasked;

    public CardPayment() {}

    public CardPayment(String paymentId, String bookingId, double amount, String paymentDate, String cardNumberMasked) {
        super(paymentId, bookingId, amount, paymentDate);
        this.cardNumberMasked = cardNumberMasked;
    }

    @Override
    public String processPayment() {
        return "Processed Card Payment for amount: $" + amount;}

    public String getCardNumberMasked() { return cardNumberMasked; }
    public void setCardNumberMasked(String cardNumberMasked)
    { this.cardNumberMasked = cardNumberMasked; }

    @Override
    public String toString() {
        return super.toString() + "|" + cardNumberMasked;
    }
}
