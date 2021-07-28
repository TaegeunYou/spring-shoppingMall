package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import study.shoppingmall.service.MemberService;


@Slf4j
@Controller
public class HomeController {

    @GetMapping({"/", "/home"})  //첫번째 화면
    public String home() {
        log.info("home controller");
        return "index";
    }

    //내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "/login";
    }


    //어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/login";
    }
}
