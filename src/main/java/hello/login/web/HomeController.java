package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    // @GetMapping("/")
    public String home() {
        return "home";
    }

    // @GetMapping("/")
    public String loginHomeV1(@CookieValue(name = "memberId", required = false) Long value, Model model) {
        if (value == null) {
            return "home";
        }

        // View에 띄우기 위해, model에 loginMember 정보 전달
        Member loginMember = memberRepository.findById(value);
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    // @GetMapping("/")
    public String loginHomeV2(HttpServletRequest request, Model model) {
        // 세션 관리자에 저장된 회원 정보 조회
        Member session = (Member) sessionManager.getSession(request);

        if (session == null) {
            return "home";
        }

        // View에 띄우기 위해, model에 loginMember 정보 전달
        model.addAttribute("member", session);
        return "loginHome";
    }

    // @GetMapping("/")
    public String loginHomeV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        // 세션 테이블 검색
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "home";
        }

        // View에 띄우기 위해, model에 loginMember 정보 전달
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


    // @GetMapping("/")
    public String loginHomeV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        // View에 띄우기 위해, model에 loginMember 정보 전달
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String loginHomeV3ArgumentResolver(@Login Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        // View에 띄우기 위해, model에 loginMember 정보 전달
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}