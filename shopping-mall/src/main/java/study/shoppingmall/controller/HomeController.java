package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.service.MemberService;
import study.shoppingmall.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping({"/", "/home"})  //첫번째 화면
    public String home() {
        log.info("home controller");
        return "index";
    }

    @GetMapping({"/newProduct"})  //NEW 신상품
    public String newProduct() {
        return "newProduct";
    }

    @GetMapping({"/bed"})  //침대
    public String bed() {
        return "bed";
    }







    //어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/login";
    }

    @PostMapping("/good")
    public String qwe() {
        log.info("zxc");
        return "redirect:/";
    }


}
