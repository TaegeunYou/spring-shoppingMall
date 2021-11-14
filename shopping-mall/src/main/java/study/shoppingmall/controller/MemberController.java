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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.shoppingmall.Validator.MemberValidator;
import study.shoppingmall.dto.*;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CartService cartService;
    private final OrderService orderService;
    private final SendEmailService sendEmailService;
    private final CommentService commentService;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String dispSignup(MemberDto memberDto) {
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {

        //유효성 검사
        MemberValidator memberValidator = new MemberValidator(memberRepository);
        memberValidator.validate(memberDto, errors);

        if (errors.hasErrors()) {
//            log.info(errors.toString());

            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("memberDto", memberDto);

            return "/member/signup";
        }
        memberService.join(memberDto);

        return "redirect:/";
    }

    /**
     * 로그인
     */
    @GetMapping("/loginform")
    public String dispLogin() {
        return "/member/login";
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
        return "/member/login";
    }

    /**
     * 내 정보 페이지
     */
    @GetMapping("/user/info")
    public String dispMyInfo(MemberDto memberDto, Model model) throws Exception {

        memberDto = memberService.memberIdToDto();
        model.addAttribute("memberDto", memberDto);

        return "/member/mypage";
    }

    /**
     * 내 정보 수정
     */
    @PostMapping("/user/info")
    public String execMyInfo(@Valid MemberDto memberDto, Errors errors, Model model) {

        if (errors.hasErrors()) {
//            log.info(errors.toString());

            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("memberDto", memberDto);

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
        List<CartDto> cartList = cartService.findCartList(memberService.findMyId());
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

        return "/member/cart";
    }

    /**
     * 장바구니 취소
     */
    @PostMapping("/user/cart")
    public String cancelCart(@RequestParam("id") Long id, Model model) {
        cartService.deleteCart(id);
        return "redirect:/user/cart";
    }

    /**
     * 주문 목록
     */
    @GetMapping("/user/orderlist")
    public String orderList(Model model) {

        List<DeliveryDto> orders = orderService.findOrders(memberService.findMyId());
        model.addAttribute("orders", orders);

        return "/member/orderList";
    }

    /**
     * 비밀번호 찾기 (네이버 이메일을 통한 임시 비밀번호 발급)
     */

    @GetMapping("/check/findPw")    //Email과 name의 일치여부를 check하는 컨트롤러
    public @ResponseBody Map<String, Boolean> pw_find(String userEmail, String userName) {

        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.memberEmailCheck(userEmail, userName);

        json.put("check", pwFindCheck);
        return json;
    }

    @PostMapping("/check/findPw") //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    public @ResponseBody void sendEmail(String userEmail, String userName) {

        MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
        sendEmailService.mailSend(dto);
    }

    /**
     * 상품 리뷰 작성
     */
    @GetMapping("/review")
    public String reviewForm(@RequestParam(value = "productName") String productName, CommentDto commentDto, Model model) {

        commentDto.setProductName(productName);
        model.addAttribute("commentDto", commentDto);
        return "/member/review";
    }

    @PostMapping("/review")
    public String createdReview(CommentDto commentDto, Model model) {

        commentService.saveComment(commentDto, memberService.findMyId());
        model.addAttribute("commentDto", commentDto);

        return "redirect:/";
    }

}
