package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
