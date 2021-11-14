package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import study.shoppingmall.domain.Message;
import study.shoppingmall.dto.CommentDto;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.CommentService;
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
    private final CommentService commentService;

    /**
     * 상품 등록
     */
    @GetMapping("/admin/product/new")
    public String createProduct(ProductDto productDto) {
        return "/admin/createProductForm";
    }

    @PostMapping("/admin/product/new")
    public String create(@Valid ProductDto productDto, Errors errors, Model model, MultipartFile[] pictures) {

        if (errors.hasErrors()) {

            //회원가입 실패시, 입력 데이터 유지
            model.addAttribute("productDto", productDto);

            return "/admin/createProductForm";
        }

        //다중 사진 업로드
        String result = "";
        int sequence = 1;
        for(MultipartFile p : pictures){
            productService.saveFile(p, productDto.getName(), sequence);
            sequence++;
        }

        productService.saveProduct(productDto);
        return "redirect:/";
    }

    /**
     * 상품 목록 조회
     */
    @GetMapping(value = {"/search"})  //검색
    public String search(@RequestParam(value = "keyword") String keyword,
                         @RequestParam(value = "sort", defaultValue = "Pop") String sort,
                         Model model) {
        List<ProductDto> products = productService.findProducts(keyword, sort);

        int size = products.size();
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("products", products);
        return "/product/search";
    }

    /**
     * 상품 키워드 검색
     */
    @GetMapping("/{keyword}")
    public String productCategorys(@PathVariable("keyword") String keyword, Model model) {
        List<ProductDto> products = productService.findProductsByCategory(keyword);
        int size = products.size();
        model.addAttribute("size", size);
        model.addAttribute("products", products);
        return "/product/category";
    }

    @GetMapping("/{keyword}/{keywordDetail}")
    public String productCategoryes(@PathVariable("keyword") String keyword, @PathVariable("keywordDetail") String keywordDetail, Model model) {
        List<ProductDto> products = productService.findProductsByCategoryDetail(keyword, keywordDetail);
        int size = products.size();
        model.addAttribute("size", size);
        model.addAttribute("products", products);
        return "product/categoryDetail";
    }

    /**
     * 상품 수정
     */
    @GetMapping("/products/{productId}/edit")
    public String updateProductForm(@PathVariable("productId") Long productId, @ModelAttribute("dto") ProductDto productDto) {

        //변경 감지 이용
        productService.updateProduct(productId, productDto.getName(), productDto.getCategory(), productDto.getCategoryDetail(),
                productDto.getColor(), productDto.getMaterial(), productDto.getSize(),
                productDto.getOrigin(), productDto.getDetail(), productDto.getASPhone(),
                productDto.getPrice(), productDto.getStock());

        return "redirect:/";
    }

    /**
     * 상품 조회
     */
    @GetMapping({"/search/{id}"})  //상품 상세 정보(주문창)
    public String detail(@PathVariable("id") Long id, Model model) {

        ProductDto productDto = productService.findProduct(id);

        //리뷰 조회
        List<CommentDto> commentList = commentService.getCommentList(id);

        model.addAttribute("product", productDto);
        model.addAttribute("commentList", commentList);
        return "/product/detail";
    }

    /**
     * 장바구니 담기
     */
    @PostMapping(value = {"/search/{id}"}, params = "cart")
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

    @PostMapping(value = {"/search/{id}"}, params = "buy")
    public String justOrder(@PathVariable("id") String productId,
                            @RequestParam(value = "category") String category,
                            @RequestParam(value = "count") int count) {

        Long memberId = memberService.findMyId();
        cartService.addCart(memberId, Long.parseLong(productId), category, count);

        return "redirect:/order";
    }




}
