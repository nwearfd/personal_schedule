package kr.co.personal.schedule.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class CookieUtil {

    public static String getCookie(HttpServletRequest req, String key) {
        String value = "";

        final Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(key.equals(cookie.getName())){
                    try {
                        value = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        log.warn(e.getMessage(), e);
                    }
                    break;
                }
            }
        }

        return value;
    }
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        final Cookie cookie = new Cookie(name, value);

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse res, HttpServletRequest req, String name) {
        final Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    res.addCookie(cookie);
                }
            }
        }
    }
}
