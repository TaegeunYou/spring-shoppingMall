package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 저장
     */
    @Transactional
    public void saveProduct(ProductDto productDto) {
        Product product = new Product(productDto);
        productRepository.save(product);
    }

    /**
     * 상품 수정 -> 변경 감지 이용 (transactional이 끝나면서 commit할 때 flush하면서 영속성 컨텍스트에 있는 것 중에 변경된 것을 찾아 그것에 대한 업데이트 쿼리를 알아서 만들어 보낸다. 따라서 persist 안해줘도 된다.)
     */
    @Transactional
    public void updateProduct(Long productId, String name, String category, String color, String material, String size, String country, String detail, String ASPhone, int price, int stock) {
        Product findProduct = productRepository.findById(productId).get();
        findProduct.changeProduct(name, category, color, material, size, country, detail, ASPhone, price, stock);
    }

    /**
     * 전체 조회
     */
    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    /**
     * 한개 조회
     */
    public Product findById(Long productId) {
        return productRepository.findById(productId).get();
    }
}
