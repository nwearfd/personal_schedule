package kr.co.personal.schedule.dao;

import kr.co.personal.schedule.domain.ScheduleBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<ScheduleBean> selectScheduleList(@Param("userIdx") String userIdx);
    void insertSchedule(ScheduleBean schedule);
}
