package kr.co.personal.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.personal.schedule.config.JwtTokenProvider;
import kr.co.personal.schedule.domain.UserBean;
import kr.co.personal.schedule.service.UserDetailService;
import kr.co.personal.schedule.service.UserService;
import kr.co.personal.schedule.util.CookieUtil;
import kr.co.personal.schedule.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String LoginPage (@ModelAttribute("userBean") UserBean userBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String token = jwtTokenProvider.resolveToken(request);
        if(!StringUtils.isEmpty(token) && jwtTokenProvider.validateToken(token)) {
            response.sendRedirect("/");
        }
        return "auth/login";
    }

    @PostMapping("/loginProc")
    public String loginProc(@ModelAttribute("userBean") UserBean userBean, BindingResult bindingResult, HttpServletResponse res) throws IOException {
//        PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
//        String password = passwordEncode.encode("abcd1234!");
//        final String inputPw = userBean.getPassword();
//        final String loginId = userBean.getUserId();
//        if(!passwordEncoder.matches(inputPw, password)) {
//            System.out.println("비밀번호 확인");
//            res.sendRedirect("/login");
//            return null;
//        }
//        String userId = "admin";
//        final String token = jwtTokenProvider.generateToken(userId);
//        CookieUtil.addCookie(res, "X-AUTH-TOKEN-ADM", token, 60 * 60 * 24);
//        res.sendRedirect("/");
//        return null;

        if(bindingResult.hasErrors()) {
            return "auth/login";
        }
        PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
        String password = passwordEncode.encode("1234");
        final String loginId = userBean.getUserId();
        final String inputPw = userBean.getPassword();
        UserBean user = userService.loginUserInfo(userBean);
        if(user == null) {
            bindingResult.addError(new ObjectError("result", "ID, 비밀번호를 확인해주세요"));
        } else {
            if(!passwordEncode.matches(inputPw, user.getPassword())) {
                bindingResult.addError(new ObjectError("result", "ID, 비밀번호를 확인해주세요"));
            }
        }
        if(bindingResult.hasErrors()) {
            return "auth/login";
        }
        final String token = jwtTokenProvider.generateToken(loginId);
        CookieUtil.addCookie(res, "X-AUTH-TOKEN-ADM", token, 60 * 60 * 24);
        res.sendRedirect("/");
        return null;
    }

    @GetMapping(value = "/logout")
    public String logoutProc(HttpServletResponse res, HttpServletRequest req) {
        CookieUtil.removeCookie(res, req, "X-AUTH-TOKEN-ADM");
        return "redirect:/auth/login";
    }
}
