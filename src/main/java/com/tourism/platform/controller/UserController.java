package com.tourism.platform.controller;

import com.tourism.platform.model.User;
import com.tourism.platform.model.AdminUser;
import com.tourism.platform.service.UserService;
import com.tourism.platform.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired private UserService us;
    @Autowired private AdminService as;

    @GetMapping("/register_page")
    public String regPage() { return "register_page"; }
    
    @PostMapping("/register")
    public String reg(@ModelAttribute User u, HttpSession s) {
        u.setRole("USER");
        us.saveUser(u);
        s.setAttribute("user", u);
        return "redirect:/?login=success";
    }

    @PostMapping("/login")
    public String log(@RequestParam String u, @RequestParam String p, HttpSession s) {
        String finalU = u != null ? u.trim() : "";
        String finalP = p != null ? p.trim() : "";
        
        User user = null;
        

        for (User x : us.getAllUsers()) {
            if (x.getUsername() != null && x.getUsername().trim().equalsIgnoreCase(finalU) && 
                x.getPassword() != null && x.getPassword().trim().equals(finalP)) {
                user = x;
                break;
            }
        }
        

        if (user == null) {
            for (AdminUser x : as.getAdmins()) {
                if (x.getUsername() != null && x.getUsername().trim().equalsIgnoreCase(finalU) && 
                    x.getPassword() != null && x.getPassword().trim().equals(finalP)) {
                    user = x;
                    break;
                }
            }
        }

        if(user != null) {
            s.setAttribute("user", user);
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/users";
            }
            return "redirect:/?login=success";
        }
        return "redirect:/?error=login";
    }

    @GetMapping("/logout")
    public String out(HttpSession s) { s.invalidate(); return "redirect:/"; }

    @GetMapping("/profile")
    public String profilePage(Model m, HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u == null) return "redirect:/";
        m.addAttribute("user", us.getUserByUsername(u.getUsername()));
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User u, @RequestParam(required = false) String newPassword, HttpSession s) {
        User sessionUser = (User) s.getAttribute("user");
        if(sessionUser == null) return "redirect:/";
        
        if(newPassword != null && !newPassword.isEmpty()) {
            u.setPassword(newPassword);
        } else {
            u.setPassword(sessionUser.getPassword());
        }
        
        us.updateUser(u);
        s.setAttribute("user", u);
        return "redirect:/profile?success=updated";
    }

    @GetMapping("/profile/delete")
    public String deleteAccount(HttpSession s) {
        User u = (User) s.getAttribute("user");
        if(u != null) {
            us.deleteUser(u.getUsername());
            s.invalidate();
            return "redirect:/?deleted=true";
        }
        return "redirect:/";
    }
}
