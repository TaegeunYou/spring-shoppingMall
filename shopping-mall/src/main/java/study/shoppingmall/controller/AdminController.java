package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.shoppingmall.dto.DeliveryDto;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.service.MemberService;
import study.shoppingmall.service.OrderService;
import study.shoppingmall.service.ProductService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/adminMemberList")
    public String adminMemberList(Model model) {

        List<MemberDto> members = memberService.findAllMembers();
        model.addAttribute("members", members);

        return "/admin/adminMemberList";
    }

    @GetMapping("/adminProductList")
    public String adminProductList(Model model) {

        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);

        return "/admin/adminProductList";
    }

    @GetMapping("/adminOrderList")
    public String adminOrderList(Model model) {

        List<DeliveryDto> orders = orderService.findALlOrdersWithDelivery();
        model.addAttribute("orders", orders);

        return "/admin/adminOrderList";
    }
}
