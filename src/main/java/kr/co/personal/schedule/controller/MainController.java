package kr.co.personal.schedule.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public void MainPage(HttpServletResponse res) throws IOException {
        res.sendRedirect("/schedule");
    }


}
