package com.tourism.platform.service;

import com.tourism.platform.model.TourPackage;
import com.tourism.platform.model.LuxuryPackage;
import com.tourism.platform.model.StandardPackage;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackageService {

    private static final String FILE_PATH = "packages.txt";

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

    public void savePackage(TourPackage p) {
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePackage(TourPackage p) {
        List<String> lines = readLines();
        List<String> updatedLines = new ArrayList<>();

        for (String l : lines) {
            if (l.startsWith(p.getId() + "|")) {
                updatedLines.add(p.toString());
            } else {
                updatedLines.add(l);
            }
        }
        writeLines(updatedLines);
    }

    public void deletePackage(String id) {
        List<String> lines = readLines();
        List<String> updatedLines = new ArrayList<>();

        for (String l : lines) {
            if (!l.startsWith(id + "|")) {
                updatedLines.add(l);
            }
        }
        writeLines(updatedLines);
    }

    public List<TourPackage> getPackages() {
        List<String> lines = readLines();
        List<TourPackage> packageList = new ArrayList<>();

        for (String l : lines) {
            try {
                String[] p = l.split("\\|");
                if (p.length >= 4) {
                    TourPackage pkg;
                    String type = p.length > 5 ? p[5] : "Standard";

                    if ("Luxury".equalsIgnoreCase(type)) {
                        pkg = new LuxuryPackage(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    } else {
                        pkg = new StandardPackage(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    }
                    packageList.add(pkg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return packageList;
    }

    public TourPackage getPackageById(String id) {
        List<TourPackage> allPackages = getPackages();
        for (TourPackage p : allPackages) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }
}

