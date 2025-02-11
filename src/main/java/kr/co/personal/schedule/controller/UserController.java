package kr.co.personal.schedule.controller;

import kr.co.personal.schedule.domain.UserBean;
import kr.co.personal.schedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserBean()); // UserBean 사용
        return "user/register";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute UserBean user) {
        userService.registerUser(user);
        return "redirect:/auth/login"; // 로그인 페이지로 리디렉션
    }
}
