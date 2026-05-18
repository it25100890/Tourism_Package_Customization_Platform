package com.tourism.platform.service;

import com.tourism.platform.model.Booking;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final String FILE_PATH = "bookings.txt";

    private void touch() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readLines() {
        touch();
        List<String> lines = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(FILE_PATH))) {
            String l;
            while ((l = r.readLine()) != null) {
                if (!l.trim().isEmpty()) lines.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void writeLines(List<String> lines) {
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : lines) {
                o.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Booking createBooking(Booking booking) {
        if (booking.getBookingId() == null) {
            booking.setBookingId((long) (1000 + new Random().nextInt(9000)));
        }
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(bookingToString(booking));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public void saveBooking(Booking booking) {
        createBooking(booking);
    }

    public List<Booking> getAllBookings() {
        return readLines().stream().map(l -> {
            try {
                String[] p = l.split("\\|");
                if (p.length >= 10) {
                    Booking b = new Booking(p[1], p[2], p[3], p[4], Integer.parseInt(p[5]), Double.parseDouble(p[6]), p[7], p[8], p[9]);
                    b.setBookingId(Long.parseLong(p[0]));
                    return b;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Booking> getBookings() {
        return getAllBookings();
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return getAllBookings().stream()
                .filter(b -> b.getUserId() != null && b.getUserId().equals(String.valueOf(userId)))
                .collect(Collectors.toList());
    }

    public Optional<Booking> getBookingById(Long id) {
        return getAllBookings().stream()
                .filter(b -> b.getBookingId().equals(id))
                .findFirst();
    }

    public Booking cancelBooking(Long id) {
        Booking b = getBookingById(id).orElse(null);
        if (b != null) {
            b.setStatus("Cancelled");
            updateBooking(b);
        }
        return b;
    }

    public void updateBooking(Booking b) {
        List<String> lines = readLines().stream()
                .map(l -> l.startsWith(b.getBookingId() + "|") ? bookingToString(b) : l)
                .collect(Collectors.toList());
        writeLines(lines);
    }

    public void deleteBooking(String id) {
        List<String> lines = readLines().stream()
                .filter(l -> !l.startsWith(id + "|"))
                .collect(Collectors.toList());
        writeLines(lines);
    }

    private String bookingToString(Booking b) {
        return b.getBookingId() + "|" + b.getUserId() + "|" + b.getPackageId() + "|" + b.getCustomizationId() + "|" + b.getTravelDate() + "|" + b.getParticipants() + "|" + b.getTotalAmount() + "|" + b.getStatus() + "|" + b.getCreatedAt() + "|" + b.getReceiptNumber();
    }
}
