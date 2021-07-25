package study.shoppingmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {

    @RequestMapping("/")  //첫번째 화면
    public String home() {

        log.info("home controller");
        return "index.html";
    }


}
