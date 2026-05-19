package com.tourism.platform.controller;

import com.tourism.platform.model.*;
import com.tourism.platform.service.AdminService;
import com.tourism.platform.service.UserService;
import com.tourism.platform.service.PackageService;
import com.tourism.platform.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired private AdminService as;
    @Autowired private UserService us;
    @Autowired private PackageService ps;
    @Autowired private BookingService bs;

    @GetMapping("/admin/packages")
    public String admPk(Model m) { m.addAttribute("packages", ps.getPackages()); return "admin_packages"; }

    @PostMapping("/admin/packages/add")
    public String addPk(@ModelAttribute TourPackage p) {
        p.setId("TP-" + java.util.UUID.randomUUID().toString().substring(0, 8));
        ps.savePackage(p);
        return "redirect:/admin/packages";
    }

    @PostMapping("/admin/packages/update")
    public String updPk(@ModelAttribute TourPackage p) { ps.updatePackage(p); return "redirect:/admin/packages"; }

    @GetMapping("/admin/packages/delete/{id}")
    public String delPk(@PathVariable String id) { ps.deletePackage(id); return "redirect:/admin/packages"; }

    @GetMapping("/admin/users")
    public String admUs(Model m) {
        m.addAttribute("users", us.getAllUsers().stream().filter(u -> "USER".equalsIgnoreCase(u.getRole())).collect(java.util.stream.Collectors.toList()));
        m.addAttribute("admins", as.getAdmins());
        m.addAttribute("bookings", bs.getBookings());
        return "admin_users";
    }

    @PostMapping("/admin/add")
    public String addAdmin(@ModelAttribute AdminUser a) {
        a.setRole("ADMIN");
        as.saveAdmin(a);
        return "redirect:/admin/users?success=admin_added";
    }

    @PostMapping("/admin/update")
    public String updateAdmin(@ModelAttribute AdminUser a) {
        a.setRole("ADMIN");
        as.updateAdmin(a);
        return "redirect:/admin/users?success=admin_updated";
    }

    @GetMapping("/admin/delete-user/{username}")
    public String delUs(@PathVariable String username) { us.deleteUser(username); return "redirect:/admin/users"; }

    @GetMapping("/admin/delete-admin/{username}")
    public String delAdm(@PathVariable String username) { as.deleteAdmin(username); return "redirect:/admin/users"; }
}
