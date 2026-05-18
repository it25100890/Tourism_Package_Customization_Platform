package com.tourism.platform.controller;

import com.tourism.platform.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired private PackageService ps;

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("packages", ps.getPackages());
        return "index";
    }
}
