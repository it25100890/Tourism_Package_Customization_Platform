package com.tourism.platform.service;

import com.tourism.platform.model.User;
import com.tourism.platform.model.AdminUser;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String FILE_PATH = "users.txt";

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

    public void saveUser(User u) {
        try (PrintWriter o = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            o.println(u.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User u) {
        List<String> lines = readLines();
        List<String> newLines = new ArrayList<>();
        
        for (String l : lines) {
            if (l.startsWith(u.getUsername() + "|")) {
                newLines.add(u.toString());
            } else {
                newLines.add(l);
            }
        }
        
        writeLines(newLines);
    }

    public void deleteUser(String username) {
        List<String> lines = readLines();
        List<String> newLines = new ArrayList<>();
        
        for (String l : lines) {
            if (!l.startsWith(username + "|")) {
                newLines.add(l);
            }
        }
        
        writeLines(newLines);
    }

    public List<User> getAllUsers() {
        List<String> lines = readLines();
        List<User> userList = new ArrayList<>();
        
        for (String l : lines) {
            try {
                String[] p = l.split("\\|");
                if (p.length >= 9) {
                    User u = new User(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
                    userList.add(u);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return userList;
    }

    public User getUserByUsername(String username) {
        List<User> allUsers = getAllUsers();
        for (User u : allUsers) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }
}
