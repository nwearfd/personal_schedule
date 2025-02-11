package kr.co.personal.schedule.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ScheduleBean {
    public Integer scheduleIdx;
    public Integer userIdx;
    public String title;
    public String content;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public String location;
}
