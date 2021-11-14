package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.shoppingmall.domain.Address;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.dto.OrderDto;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.MemberService;
import study.shoppingmall.service.OrderService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping({"/order"})  //주문창
    public String orderForm(MemberDto memberDto, Model model, OrderDto orderDto) {

        /**
         * 회원정보
         */

        memberDto = memberService.memberIdToDto();

        orderDto = new OrderDto(memberDto.getName(), memberDto.getPhone(), memberDto.getZipcode(), memberDto.getAddress(), memberDto.getAddressdetail());

        model.addAttribute("orderDto", orderDto);
        model.addAttribute("memberDto", memberDto);


        /**
         * 주문상품
         */
        //장바구니 리스트 가져오기
        List<CartDto> cartList = cartService.findCartList(memberService.findMyId());
        model.addAttribute("cartList", cartList);


        /**
         * 결제정보
         */
        long orderPrice = 0; //총 상품가격
        for (CartDto cartDto : cartList) {
            orderPrice += cartDto.getTotalPrice();
        }

        long deliveryFee = 3000; //배송비
        long discountFee = 10000; //배송비
        long totalOrderPrice = orderPrice + deliveryFee - discountFee; //총 주문가격

        model.addAttribute("discountFee", discountFee);
        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("totalOrderPrice", totalOrderPrice);

        return "/member/order";
    }

    @PostMapping({"/order"})
    public String order(OrderDto orderDto) {

        /**
         * 주문 진행
         * Order 엔티티 생성 및 저장
         */

        //배달주소
        Address address = new Address(orderDto.getZipcode(), orderDto.getAddress(), orderDto.getAddressdetail());

        //주문 상품 리스트
        List<CartDto> cartList = cartService.findCartList(memberService.findMyId());

        //주문
        orderService.order(orderDto.getReceiverName(), orderDto.getReceiverPhone(), orderDto.getDetail(), memberService.findMyId(), address, cartList);

        //장바구니 삭제
        cartService.deleteAllCart(cartList);

        return "redirect:/";
    }




}
