package com.tourism.platform.controller;

import com.tourism.platform.model.Customization;
import com.tourism.platform.model.Booking;
import com.tourism.platform.service.CustomizationService;
import com.tourism.platform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customization")
public class CustomizationController {

    @Autowired private CustomizationService cs;
    @Autowired private BookingService bs;

    @GetMapping("/add/{bookingId}")
    public String showAddForm(@PathVariable Long bookingId, Model m) {
        Customization c = new Customization();
        c.setBookingId(bookingId);
        m.addAttribute("customization", c);
        return "customize_form";
    }

    @PostMapping("/save")
    public String saveCustomization(@ModelAttribute Customization c) {
        Booking b = bs.getBookings().stream()
                .filter(x -> x.getBookingId().equals(c.getBookingId()))
                .findFirst().orElse(null);

        if (c.getCustomizationId() == null || c.getCustomizationId().isEmpty()) {
            c.setCustomizationId("VR-CUST-" + (100000 + new java.util.Random().nextInt(900000)));
            cs.saveCustomization(c);

            if (b != null) {
                b.setTotalAmount(b.getTotalAmount() + c.getAdditionalCost());
                bs.updateBooking(b);
            }
        } else {
            Customization old = cs.getCustomizations().stream()
                    .filter(x -> x.getCustomizationId().equals(c.getCustomizationId()))
                    .findFirst().orElse(null);

            double oldCost = (old != null) ? old.getAdditionalCost() : 0;
            cs.updateCustomization(c);

            if (b != null) {
                b.setTotalAmount(b.getTotalAmount() - oldCost + c.getAdditionalCost());
                bs.updateBooking(b);
            }
        }
        return "redirect:/payment/" + c.getBookingId();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model m) {
        Customization c = cs.getCustomizations().stream()
                .filter(x -> x.getCustomizationId().equals(id))
                .findFirst().orElse(null);
        m.addAttribute("customization", c);
        return "customize_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        Customization c = cs.getCustomizations().stream()
                .filter(x -> x.getCustomizationId().equals(id))
                .findFirst().orElse(null);

        if (c != null) {
            Booking b = bs.getBookings().stream()
                    .filter(x -> x.getBookingId().equals(c.getBookingId()))
                    .findFirst().orElse(null);

            if (b != null) {
                b.setTotalAmount(b.getTotalAmount() - c.getAdditionalCost());
                bs.updateBooking(b);
            }
            cs.deleteCustomization(id);
        }
        return "redirect:/my-bookings";
    }
}
