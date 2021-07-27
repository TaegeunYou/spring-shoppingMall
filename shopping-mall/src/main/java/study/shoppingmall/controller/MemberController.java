package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.shoppingmall.service.MemberService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String dispSignup(MemberDto memberDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            log.info(errors.toString());
            log.info(errors.getAllErrors().toString());

            log.info("errrrrr");
            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("memberDto", memberDto);

            //유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/signup";
        }
        log.info("gdddddd");

        memberService.join(memberDto);

        return "redirect:/";
    }
}
