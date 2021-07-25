package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Product;
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
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void updateProduct(Long productId, String name, int price, int stock, String detail) {
        Product findProduct = productRepository.findById(productId).get();
        findProduct.changeProduct(name, price, stock, detail);
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
