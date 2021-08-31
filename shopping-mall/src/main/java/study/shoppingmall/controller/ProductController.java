package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import study.shoppingmall.domain.Message;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.MemberService;
import study.shoppingmall.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    /**
     * 상품 등록
     */
    @GetMapping("/product/new")
    public String createProduct(ProductDto productDto) {
        return "/createProductForm";        //프론트단 구현해야됨
    }

    @PostMapping("/product/new")
    public String create(@Valid ProductDto productDto, Errors errors, Model model) {

        if (errors.hasErrors()) {

            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("productDto", productDto);

            //유효성 통과 못한 필드와 메시지를 핸들링
//            Map<String, String> validatorResult = productService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }

            return "/createProductForm";
        }

        productService.saveProduct(productDto);
        return "redirect:/";    // 상품 상세 정보 페이지로 바꿔줘고 프로트단 구현해야됨
    }


    /**
     * 상품 목록 조회
     */
//    @GetMapping("products")
//    public String list(Model model) {
//        List<Product> products = productService.findProducts();
//        model.addAttribute("products", products);
//        return "products/productList";      //프론트단 구현해야됨
//    }
    @GetMapping("/search")  //검색
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<ProductDto> products = productService.findProducts(keyword);
        model.addAttribute("products", products);
        return "search";
    }

    @GetMapping("category/{keyword}")
    public String productCategory(@PathVariable("keyword") String keyword, Model model) {
        List<ProductDto> products = productService.findProductsByCategory(keyword);
        model.addAttribute("products", products);
        return "category";
    }

    /**
     * 상품 수정
     */
    @GetMapping("products/{productId}/edit")
    public String updateProductForm(@PathVariable("productId") Long productId, @ModelAttribute("dto") ProductDto productDto) {

        //변경 감지 이용
        productService.updateProduct(productId, productDto.getName(), productDto.getCategory(),
                productDto.getColor(), productDto.getMaterial(), productDto.getSize(),
                productDto.getOrigin(), productDto.getDetail(), productDto.getASPhone(),
                productDto.getPrice(), productDto.getStock());

        return "redirect:/";    //페이지 바꿔줘야됨
    }

    /**
     * 상품 조회
     */
    @GetMapping({"/detail/{id}"})  //상품 상세 정보(주문창)
    public String detail(@PathVariable("id") Long id, Model model) {
        ProductDto productDto = productService.getProductDto(id);

        model.addAttribute("product", productDto);
        return "detail";
    }

    /**
     * 장바구니 담기
     */
    @PostMapping(value = {"/detail/{id}"}, params = "cart")
    public ModelAndView cart(@PathVariable("id") Long productId,
                       @RequestParam(value = "category") String category,
                       @RequestParam(value = "count") int count,
                       ModelAndView mav) {

        Long memberId = memberService.findMyId();
        cartService.addCart(memberId, productId, category, count);

        mav.addObject("data", new Message("장바구니에 담겼습니다.", "/"));
        mav.setViewName("Message");

        return mav;
    }

    @PostMapping(value = {"/detail/{id}"}, params = "buy")
    public String justOrder(@PathVariable("id") String productId,
                            @RequestParam(value = "category") String category,
                            @RequestParam(value = "count") int count) {

        Long memberId = memberService.findMyId();
        cartService.addCart(memberId, Long.parseLong(productId), category, count);

        return "redirect:/order";
    }
}
