package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.shoppingmall.Validator.MemberValidator;
import study.shoppingmall.domain.Cart;
import study.shoppingmall.domain.Member;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.dto.LoginDto;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.MemberService;
import study.shoppingmall.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CartService cartService;

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

    /**
     * 내 정보 페이지
     */
    @GetMapping("/user/info")
    public String dispMyInfo(MemberDto memberDto, Model model) throws Exception {

        memberDto = memberService.memberIdToDto();

//        String MemberName = member.getName();
//        String MemberPhone = member.getPhone();
//
//        log.info("C: 회원정보보기 GET의 아이디 "+ MemberName);
//        log.info("C: 회원정보보기 GET의 아이디 "+ MemberEmail);
//        log.info("C: 회원정보보기 GET의 아이디 "+ MemberPhone);
//
//        model.addAttribute("username", MemberName);

          model.addAttribute("memberDto", memberDto);


        return "/mypage";
    }

    /**
     * 내 정보 수정
     */
    @PostMapping("/user/info")
    public String execMyInfo(@Valid MemberDto memberDto, Errors errors, Model model) {

        //유효성 검사
//        MemberValidator memberValidator = new MemberValidator(memberRepository);
//        memberValidator.validate(memberDto, errors);
//        memberDto.setEmail(memberDto.getEmail());

        if (errors.hasErrors()) {
            log.info(errors.toString());


            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("memberDto", memberDto);


//            //유효성 통과 못한 필드와 메시지를 핸들링
//            Map<String, String> validatorResult = memberService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }

            return "/user/info";
        }
        memberService.update(memberDto);

        return "redirect:/";
    }

    /**
     * 장바구니 목록
     */
    @GetMapping("/user/cart")
    public String cartList(Model model) {

        //장바구니 리스트 가져오기
        List<CartDto> cartList = cartService.cartToCartDto(cartService.findCartList(memberService.findMyId()));
        model.addAttribute("cartList", cartList);

        int orderPrice = 0; //총 상품가격
        for (CartDto cartDto : cartList) {
            orderPrice += cartDto.getTotalPrice();
        }

        int deliveryFee = 3000; //배송비
        int totalOrderPrice = orderPrice + deliveryFee; //총 주문가격

        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("totalOrderPrice", totalOrderPrice);

        return "cart";
    }

    /**
     * 장바구니 취소
     */
    @PostMapping("/user/cart")
    public String cancelCart(@RequestParam("id") Long id, Model model) {
        cartService.deleteCart(id);
        return "redirect:/user/cart";
    }

}
