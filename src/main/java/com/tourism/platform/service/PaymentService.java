package com.tourism.platform.service;

import com.tourism.platform.model.Payment;
import com.tourism.platform.model.CardPayment;
import com.tourism.platform.model.CashPayment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class PaymentService {

    private static final String FILE_PATH = "payments.txt";

    private void touch() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePayment(Payment p) {
        touch();
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getPayments() {
        touch();
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(FILE_PATH))) {
            String l;
            while ((l = r.readLine()) != null) {
                if (l.trim().isEmpty()) continue;
                String[] p = l.split("\\|");
                try {
                    String type = p[4];
                    if (type.equals("CardPayment") && p.length >= 6) {
                        payments.add(new CardPayment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[5]));
                    } else if (type.equals("CashPayment") && p.length >= 6) {
                        payments.add(new CashPayment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[5]));
                    }
                } catch (Exception ex) {
                    System.err.println("Malformed payment: " + l);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
