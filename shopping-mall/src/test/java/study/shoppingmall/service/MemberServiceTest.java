package study.shoppingmall.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Member;
import study.shoppingmall.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("m1@naver.com", "1", "member1");
        memberService.join(member);

        //when
        Member findMember = memberRepository.findByEmail("m1@naver.com").get(0);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);

    }

    @Test
    public void 중복_이메일_예외() throws Exception {
        //given
        Member member1 = new Member("m1@naver.com", "1", "member1");
        Member member2 = new Member("m1@naver.com", "2", "member2");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));


    }
}