package com.blogplatform.blog_platform.controller;

import com.blogplatform.blog_platform.entity.User;
import com.blogplatform.blog_platform.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager; // ✅ 생성자 주입
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 회원 가입 폼
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // 빈 객체 전달
        return "register";
    }

    // 회원 가입 처리
    @PostMapping("/register")
    public String register(User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "register";
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 존재하는 닉네임입니다.");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        try {
            // 🚀 회원가입 후 자동 로그인 처리 (Spring Security 방식)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/login"; // 로그인 실패 시 로그인 페이지로 이동
        }

        return "redirect:/"; // 🚀 자동 로그인 후 대시보드 이동
    }
}