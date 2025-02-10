package kr.co.personal.schedule.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain; // jakarta.servlet 임포트
import jakarta.servlet.ServletException; // jakarta.servlet 임포트
import jakarta.servlet.http.HttpServletRequest; // jakarta.servlet 임포트
import jakarta.servlet.http.HttpServletResponse; // jakarta.servlet 임포트
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }
    private final JwtTokenProvider jwtTokenProvider;


    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        jwtTokenProvider = provider;
    }

//    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String path = request.getRequestURI();
        return path.startsWith("/auth") || path.startsWith("/public") || path.startsWith("/ping") || path.startsWith("/monitor/status-pre-evx");
    }

//    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = jwtTokenProvider.resolveToken(req);
        if (!isEmpty(token) && jwtTokenProvider.validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        } else {
            if(isAjaxRequest(req)) {
                DefaultAlertAndGo(res, "로그인 세션이 만료되었습니다. 로그인 화면으로 이동합니다.", "/");
            } else {
                res.sendRedirect("/auth/login");
            }
            return;
        }

        filterChain.doFilter(req, res);
    }

    public static void DefaultAlertAndGo(HttpServletResponse response, String msg, String url) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().printf("<script>alert('%s');location.href='%s';</script>", msg, url);
    }

    private boolean isAjaxRequest(HttpServletRequest req) {
        return "XMLHttpRequest".equals(req.getHeader("x-requested-with"));
    }
}
