package com.tourism.platform.service;

import com.tourism.platform.model.AdminUser;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private static final String FILE_PATH = "admins.txt";

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

    public void saveAdmin(AdminUser a) {
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(a.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAdmin(AdminUser a) {
        List<String> lines = readLines().stream()
                .map(l -> l.startsWith(a.getUsername() + "|") ? a.toString() : l)
                .collect(Collectors.toList());
        writeLines(lines);
    }

    public void deleteAdmin(String username) {
        List<String> lines = readLines().stream()
                .filter(l -> !l.startsWith(username + "|"))
                .collect(Collectors.toList());
        writeLines(lines);
    }

    public List<AdminUser> getAdmins() {
        return readLines().stream().map(l -> {
            try {
                String[] p = l.split("\\|");
                if (p.length >= 10) {
                    return new AdminUser(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[9]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public AdminUser getAdminByUsername(String username) {
        return getAdmins().stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }
}