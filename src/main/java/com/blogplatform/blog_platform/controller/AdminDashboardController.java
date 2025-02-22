package com.blogplatform.blog_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        // 대시보드 데이터 (테스트용 임시 데이터)
        model.addAttribute("postCount", 120); // 총 게시글 수
        model.addAttribute("userCount", 50); // 총 사용자 수
        model.addAttribute("visitorCount", 5000); // 총 방문자 수

        // 최근 게시글 리스트 (테스트 데이터)
        List<Map<String, String>> recentPosts = List.of(
                Map.of("title", "첫 번째 게시글", "author", "관리자", "createdAt", "2025-02-22"),
                Map.of("title", "두 번째 게시글", "author", "사용자1", "createdAt", "2025-02-21")
        );
        model.addAttribute("recentPosts", recentPosts);

        return "admin/dashboard";
    }


}
