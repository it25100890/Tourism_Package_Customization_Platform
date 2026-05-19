package com.tourism.platform.controller;

import com.tourism.platform.model.*;
import com.tourism.platform.service.BookingService;
import com.tourism.platform.service.PackageService;
import com.tourism.platform.service.CustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
public class BookingController {
    @Autowired private BookingService bs;
    @Autowired private PackageService ps;
    @Autowired private CustomizationService cs;
    @Autowired private com.tourism.platform.service.PaymentService pays;

    @GetMapping("/customize")
    public String custGeneric(Model m, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/register_page";
        m.addAttribute("tp", new TourPackage());
        return "customize";
    }

    @GetMapping("/customize/{id}")
    public String cust(@PathVariable String id, Model m, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/register_page";
        m.addAttribute("tp", ps.getPackages().stream().filter(x->x.getId().equals(id)).findFirst().orElse(new TourPackage()));
        return "customize";
    }

    @GetMapping("/book/direct/{id}")
    public String bookDirect(@PathVariable String id, @RequestParam(required = false, defaultValue = "See Details") String travelDate, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u==null) return "redirect:/register_page";

        TourPackage tp = ps.getPackageById(id);
        if(tp == null) return "redirect:/packages";

        Booking b = new Booking();
        b.setUserId(u.getUsername());
        b.setPackageId(tp.getId());

        if (travelDate == null || travelDate.trim().isEmpty()) {
            travelDate = "See Details";
        }
        b.setTravelDate(travelDate);

        b.setTotalAmount(tp.getPrice());
        b.setStatus("Pending Payment");
        b.setParticipants(1);

        b = bs.createBooking(b);
        return "redirect:/payment/" + b.getBookingId();
    }

    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable Long id, Model m, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/";

        Booking b = bs.getBookings().stream()
                .filter(x -> x.getBookingId().equals(id) && u.getUsername().equalsIgnoreCase(x.getUserId()))
                .findFirst().orElse(null);

        if(b == null) return "redirect:/my-bookings";

        TourPackage tp = ps.getPackageById(b.getPackageId());
        if (tp == null) tp = new TourPackage();

        m.addAttribute("tp", tp);
        m.addAttribute("booking", b);
        return "customize";
    }

    @PostMapping("/book")
    public String book(@ModelAttribute Booking b, @RequestParam(defaultValue = "1") int persons, @RequestParam(defaultValue = "0") double totalCost, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u==null) return "redirect:/register_page";

        TourPackage tp = ps.getPackageById(b.getPackageId());

        if (tp != null) {
            double discount = tp.calculateDiscount(persons);
            b.setTotalAmount((tp.getPrice() * persons) - discount);
        } else if (totalCost > 0) {
            b.setTotalAmount(totalCost);
        }
        b.setParticipants(persons);

        if(b.getBookingId() != null) {
            final Long currentBookingId = b.getBookingId();
            Booking existing = bs.getBookings().stream()
                    .filter(x -> x.getBookingId().equals(currentBookingId))
                    .findFirst().orElse(null);
            if(existing != null) {
                b.setUserId(u.getUsername());
                b.setStatus(existing.getStatus());
                bs.updateBooking(b);
                return "redirect:/my-bookings?update=success";
            }
        }

        b.setUserId(u.getUsername());
        b.setStatus("Pending Payment");
        Booking savedBooking = bs.createBooking(b);
        return "redirect:/payment/" + savedBooking.getBookingId();
    }



    @GetMapping("/my-bookings")
    public String myBookings(Model m, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/";

        List<Booking> userBookings = bs.getBookings().stream()
                .filter(b -> u.getUsername().equalsIgnoreCase(b.getUserId()))
                .collect(java.util.stream.Collectors.toList());

        m.addAttribute("bookings", userBookings);
        m.addAttribute("customizations", cs.getCustomizations());
        return "my-bookings";
    }

    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable String id, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/";

        boolean wasPaid = false;
        try {
            Long bId = Long.parseLong(id);
            Booking b = bs.getBookings().stream().filter(x -> x.getBookingId().equals(bId)).findFirst().orElse(null);
            if(b != null && "Paid".equals(b.getStatus())) {
                wasPaid = true;
            }
        } catch (Exception e) {}

        bs.deleteBooking(id);

        if (wasPaid) {
            return "redirect:/my-bookings?refund=true";
        }
        return "redirect:/my-bookings";
    }
}