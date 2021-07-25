package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
