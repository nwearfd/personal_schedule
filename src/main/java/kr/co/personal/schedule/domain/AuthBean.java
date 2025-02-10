package kr.co.personal.schedule.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class AuthBean implements GrantedAuthority {
    private String authority;
    private String roleName;
    private String description;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    private static final long serialVersionUID = 1L;
}
