package com.tourism.platform.controller;

import com.tourism.platform.model.Payment;
import com.tourism.platform.model.CardPayment;
import com.tourism.platform.model.CashPayment;
import com.tourism.platform.model.Booking;
import com.tourism.platform.service.PaymentService;
import com.tourism.platform.service.BookingService;
import com.tourism.platform.service.PackageService;
import com.tourism.platform.service.CustomizationService;
import com.tourism.platform.model.TourPackage;
import com.tourism.platform.model.Customization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private CustomizationService customizationService;

    @GetMapping("/payment/{id}")
    public String payPage(@PathVariable Long id, Model model) {
        Booking b = bookingService.getBookingById(id).orElse(null);
        model.addAttribute("booking", b);
        return "payment";
    }

    @PostMapping("/process-payment")
    public String processPay(@RequestParam Long bookingId, @RequestParam String method) {
        List<Booking> all = bookingService.getBookings();
        for(Booking b : all) {
            if(b.getBookingId().equals(bookingId)) {
                String pId = "PAY-" + (1000 + new Random().nextInt(9000));
                String date = new java.util.Date().toString();
                Payment payment;
                if ("Card".equalsIgnoreCase(method)) {
                    payment = new CardPayment(pId, String.valueOf(bookingId), b.getTotalAmount(), date, "****-****-****-1234");
                    b.setStatus("Paid");
                } else {
                    payment = new CashPayment(pId, String.valueOf(bookingId), b.getTotalAmount(), date, "REC-CASH-" + new Random().nextInt(1000));
                    b.setStatus("Reserved");
                }
                bookingService.updateBooking(b);

                System.out.println(payment.processPayment());
                paymentService.savePayment(payment);

                break;
            }
        }
        return "redirect:/payment/receipt/" + bookingId;
    }

    @GetMapping("/payment/receipt/{id}")
    public String receiptPage(@PathVariable Long id, Model model) {
        Booking b = bookingService.getBookingById(id).orElse(null);
        if (b == null) return "redirect:/my-bookings";

        TourPackage pkg = packageService.getPackageById(b.getPackageId());
        Customization cust = customizationService.getCustomizationByBookingId(b.getBookingId());

        String duration = "3 Days, 2 Nights";
        String endDate = "See Details";
        if (b.getTravelDate() != null && b.getTravelDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(b.getTravelDate());
                endDate = date.plusDays(3).toString();
            } catch (Exception e) {}
        }

        model.addAttribute("booking", b);
        model.addAttribute("tourPackage", pkg);
        model.addAttribute("customization", cust);
        model.addAttribute("duration", duration);
        model.addAttribute("endDate", endDate);
        return "receipt";
    }
}
