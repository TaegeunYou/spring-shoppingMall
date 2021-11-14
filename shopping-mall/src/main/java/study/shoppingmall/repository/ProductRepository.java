package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingOrCategoryContaining(String keyword1, String keyword2);

    List<Product> findByNameContainingOrCategoryContainingOrderByPriceAsc(String keyword1, String keyword2);

    List<Product> findByNameContainingOrCategoryContainingOrderByPriceDesc(String keyword1, String keyword2);

    List<Product> findByCategory(String keyword);

    List<Product> findByCategoryAndCategoryDetail(String keyword, String keywordDetail);

    List<Product> findByName(String name);

    List<Product> findByNameContainingOrCategoryContainingOrderByReviewDesc(String keyword1, String keyword2);

    List<Product> findByNameContainingOrCategoryContainingOrderByRatingDesc(String keyword1, String keyword2);

}
