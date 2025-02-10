package kr.co.personal.schedule.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserBean {
    public Integer userIdx;
    public String userId;
    public String userName;
    public String name;
    public String password;
    public String email;
    public String phone;
    public String role;


    private static final long serialVersionUID = 1L;
}
