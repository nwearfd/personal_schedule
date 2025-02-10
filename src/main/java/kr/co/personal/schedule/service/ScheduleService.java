package kr.co.personal.schedule.service;

import kr.co.personal.schedule.config.JwtTokenProvider;
import kr.co.personal.schedule.dao.ScheduleMapper;
import kr.co.personal.schedule.domain.ScheduleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    public List<ScheduleBean> selectScheduleList() {
        String userIdx = jwtTokenProvider.getUserIdx();
        return scheduleMapper.selectScheduleList(userIdx);
    }
    public void registerSchedule(ScheduleBean schedule) {
        schedule.setUserIdx(Integer.valueOf(jwtTokenProvider.getUserIdx()));
        scheduleMapper.insertSchedule(schedule); // ScheduleMapper의 insertSchedule 메서드 사용
    }
}
