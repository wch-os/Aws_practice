package hello.login.web.session;

import hello.login.domain.member.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private Map<String, Object> sessionTable = new ConcurrentHashMap<>();

    // 1. 세션을 생성하고, 세션 테이블에 저장하기
    // Response에 담아야 한다.
    public void createSession(Object value, HttpServletResponse response) {

        // 세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionTable.put(sessionId, value);

        // 쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    // 2. 세션 만료
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        // 세션 테이블에서 제거
        if (sessionCookie != null) {
            sessionTable.remove(sessionCookie.getValue());
        }

    }

    // 3. 세션 조회 (Request 요청)
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        
        if (sessionCookie == null) {
            return null;
        }

        // 세션 테이블에 세션 id와 일치한 데이터가 있다면 반환
        return sessionTable.get(sessionCookie.getValue());
    }

    // 여러 쿠키 목록 중, 세션 쿠키 찾기
    public Cookie findCookie(HttpServletRequest request, String CookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(CookieName))
                .findAny()
                .orElse(null);
    }
}
