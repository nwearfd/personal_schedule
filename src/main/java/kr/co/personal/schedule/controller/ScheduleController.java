package kr.co.personal.schedule.controller;

import kr.co.personal.schedule.domain.ScheduleBean;
import kr.co.personal.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @GetMapping("")
    public String scheduleList(Model model) {
        model.addAttribute("list", scheduleService.selectScheduleList());
        return "schedule/schedule-list";
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("schedule", new ScheduleBean()); // ScheduleBean 사용
        return "schedule/schedule-register";
    }

    @PostMapping("/save")
    public String registerSchedule(@ModelAttribute ScheduleBean schedule) {
        scheduleService.registerSchedule(schedule);
        return "redirect:/schedule"; // 일정 목록 페이지로 리디렉션
    }
}