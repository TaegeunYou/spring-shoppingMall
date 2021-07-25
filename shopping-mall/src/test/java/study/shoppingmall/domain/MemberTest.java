package study.shoppingmall.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Member member1 = new Member("m1@naver.com", "1", "member1");
        Member member2 = new Member("m2@naver.com", "2", "member2");
        Member member3 = new Member("m3@naver.com", "3", "member3");
        Member member4 = new Member("m4@naver.com", "4", "member4");

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member.getId() = " + member.getId());
            System.out.println("member.getEmail() = " + member.getEmail());
        }
    }

}