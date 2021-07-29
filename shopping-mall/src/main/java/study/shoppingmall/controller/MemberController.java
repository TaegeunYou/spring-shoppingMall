package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.shoppingmall.Validator.MemberValidator;
import study.shoppingmall.dto.LoginDto;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String dispSignup(MemberDto memberDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {

        //유효성 검사
        MemberValidator memberValidator = new MemberValidator(memberRepository);
        memberValidator.validate(memberDto, errors);

        if (errors.hasErrors()) {
            log.info(errors.toString());

            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("memberDto", memberDto);


//            //유효성 통과 못한 필드와 메시지를 핸들링
//            Map<String, String> validatorResult = memberService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }

            return "/signup";
        }
        memberService.join(memberDto);

        return "redirect:/";
    }

    /**
     * 로그인
     */
    @GetMapping("/loginform")
    public String dispLogin(LoginDto loginDto) {
        return "/login";
    }

    @GetMapping("/login-error")
    public String loginfail(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = "이메일 또는 비밀번호를 다시 확인해주세요.";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "/login";
    }



}
