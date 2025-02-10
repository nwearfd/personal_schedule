package kr.co.personal.schedule.service;

import kr.co.personal.schedule.config.JwtTokenProvider;
import kr.co.personal.schedule.dao.UserMapper;
import kr.co.personal.schedule.domain.UserBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//    public String login(UserBean user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // JWT 생성
//        List<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        String token = jwtTokenProvider.generateToken(user.getUsername(), roles);
//
//        return token;
//    }

    public UserBean loginUserInfo(UserBean userBean) {
//        UserBean user = new UserBean(userBean.getUserId());
        UserBean user = userMapper.loginAuthentication(userBean);

        return user;
    }
}
