package study.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shoppingmall.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmail(String email);
    List<Member> findByNickname(String nickname);
    Optional<Member> findOptionalByEmail(String email);



}
