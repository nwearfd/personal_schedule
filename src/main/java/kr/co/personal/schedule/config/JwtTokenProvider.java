package kr.co.personal.schedule.config;


import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.personal.schedule.util.CookieUtil;
import kr.co.personal.schedule.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final int jwtExpirationMsec = 1000 * 60 * 60 * 8;

    private static final String userIdx = "userIdx";
    private static final String userId = "userId";
    public String generateToken(String user) {

        final Date now = new Date();
        final Date expireDate = new Date(now.getTime() + jwtExpirationMsec);
        final Claims claims = Jwts.claims().setSubject(user);
        claims.put(userIdx, '1');
        claims.put(userId, user);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Claims getClaims(String token) {
        Claims claims;

        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Could not get all claims Token from passed token");
            claims = null;
        }

        return claims;
    }

    public String getUserIdx() {
        final String token = resolveToken(getRequest());
        if(!StringUtils.isEmpty(token) && validateToken(token)) {
            final Claims claims = getClaims(token);
            if(claims != null) {
                return (String) claims.get(userIdx);
            }
        }
        return null;
    }
    public String getUserId() {
        final String token = resolveToken(getRequest());
        if(!StringUtils.isEmpty(token) && validateToken(token)) {
            final Claims claims = getClaims(token);
            if(claims != null) {
                return (String) claims.get(userId);
            }
        }
        return null;
    }
    public String resolveToken(HttpServletRequest req) {
        String token = null;
        try {
            final String value = CookieUtil.getCookie(req, "X-AUTH-TOKEN-ADM");
            if(value != null) {
                token = value;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return token;
    }



    public  boolean validateToken(String token) {
        try {
            final Jws<Claims> claims = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException se) {
            log.warn("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.warn("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.warn("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.warn("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty.");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = getClaims(token);
        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("userId").toString().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        final org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private HttpServletRequest getRequest() {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null) {
            return (HttpServletRequest) servletRequestAttributes.getRequest();
        }

        return null;
    }
}
