package kr.co.personal.schedule.dao;

import kr.co.personal.schedule.domain.AuthBean;
import kr.co.personal.schedule.domain.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserBean loginAuthentication(UserBean user);
    List<AuthBean> selectAdminAuthorities(UserBean user);
    void insertUser(UserBean user);
}
