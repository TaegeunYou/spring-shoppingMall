package study.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
    @GetMapping("products")
    public String list(Model model) {
        List<Product> products = productService.findProducts();
        model.addAttribute("products", products);
        return "products/productList";      //프론트단 구현해야됨
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
}
