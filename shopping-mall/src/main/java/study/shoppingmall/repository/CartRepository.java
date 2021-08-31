package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMemberId(Long id);
    long deleteAllByMemberId(Long id);
}
