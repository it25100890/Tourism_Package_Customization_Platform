package com.tourism.platform.service;

import com.tourism.model.Customization;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomizationService {

    private static final String FILE_PATH = "customizations.txt";

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

    public void saveCustomization(Customization c) {
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(c.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //abstraction
    public void updateCustomization(Customization c) {
        List<String> lines = readLines();
        int i = 0;
        while (i < lines.size()) {
            if (lines.get(i).startsWith(c.getCustomizationId() + "|")) {
                lines.set(i, c.toString());
                break;
            }
            i++;
        }
        writeLines(lines);
    }

    public void deleteCustomization(String id) {
        List<String> lines = readLines();
        List<String> filteredLines = new ArrayList<>();
        int i = 0;
        while (i < lines.size()) {
            if (!lines.get(i).startsWith(id + "|")) {
                filteredLines.add(lines.get(i));
            }
            i++;
        }
        writeLines(filteredLines);
    }

    public List<Customization> getCustomizations() {
        List<String> lines = readLines();
        List<Customization> customizations = new ArrayList<>();
        int i = 0;
        while (i < lines.size()) {
            try {
                String[] p = lines.get(i).split("\\|");
                if (p.length >= 10) {
                    customizations.add(new Customization(p[0], Long.parseLong(p[1]), p[2], p[3], p[4], p[5], Boolean.parseBoolean(p[6]), Boolean.parseBoolean(p[7]), Boolean.parseBoolean(p[8]), Double.parseDouble(p[9])));
                } else if (p.length == 9) {
                    customizations.add(new Customization(p[0], Long.parseLong(p[1]), p[2], p[3], p[4], p[5], Boolean.parseBoolean(p[6]), Boolean.parseBoolean(p[7]), false, Double.parseDouble(p[8])));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        return customizations;
    }

    public Customization getCustomizationByBookingId(Long bookingId) {
        List<Customization> all = getCustomizations();
        int i = 0;
        while (i < all.size()) {
            if (all.get(i).getBookingId().equals(bookingId)) {
                return all.get(i);
            }
            i++;
        }
        return null;
    }
}
