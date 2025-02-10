package kr.co.personal.schedule.service;

import kr.co.personal.schedule.dao.UserMapper;
import kr.co.personal.schedule.domain.AuthBean;
import kr.co.personal.schedule.domain.UserBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDetailService{
    @Autowired
    UserMapper userMapper;


}
