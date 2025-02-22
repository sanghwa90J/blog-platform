package com.blogplatform.blog_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "관리자 대시보드");
        return "admin/dashboard";
    }

}
