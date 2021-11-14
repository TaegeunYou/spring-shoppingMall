package study.shoppingmall.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Product;
import study.shoppingmall.dto.CommentDto;
import study.shoppingmall.dto.ProductDto;
import study.shoppingmall.repository.CommentRepository;
import study.shoppingmall.repository.ProductRepository;
import study.shoppingmall.service.CommentService;
import study.shoppingmall.service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class CartTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void 상품생성() throws Exception {

        //given
        Long memberId1 = new Long(1);

        //when
        String productName1 = "상품1";
        ProductDto productDto = new ProductDto(productName1, "Bed", "Super-Single",
                "white", "나무", "10x20 cm", "한국",
                "01077777777", "상품입니다.", 10000, 10);

        productService.saveProduct(productDto);

        Product product = productRepository.findByNameContainingOrCategoryContainingOrderByPriceAsc("상품1", "상품1").get(0);
        String productName2 = product.getName();

        //then
        Assertions.assertThat(productName1).isEqualTo(productName2);



        List<Product> products = em.createQuery("select p from Product p", Product.class)
                .getResultList();

        for (Product p : products) {
            System.out.println("member.getId() = " + p.getId());
            System.out.println("member.getId() = " + p.getName());
        }



    }

}