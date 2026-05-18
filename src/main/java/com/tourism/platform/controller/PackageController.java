package com.tourism.platform.controller;

import com.tourism.platform.model.TourPackage;
import com.tourism.platform.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageController {
    @Autowired private PackageService ps;

    @GetMapping("/packages")
    public String catalog(@RequestParam(required = false) String search, Model m) {
        List<com.tourism.platform.model.TourPackage> pkgs = ps.getPackages();
        if (search != null && !search.isEmpty()) {
            pkgs = pkgs.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                            p.getDestination().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        m.addAttribute("packages", pkgs);
        return "packageCatalog";
    }
    @GetMapping("/package/details/{id}")
    public String packageDetails(@PathVariable String id, Model m) {
        com.tourism.platform.model.TourPackage tp = ps.getPackageById(id);
        if (tp == null) {
            return "redirect:/packages";
        }
        m.addAttribute("pkg", tp);
        return "packageDetails";
    }
}
