package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
