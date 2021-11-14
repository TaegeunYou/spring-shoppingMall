package study.shoppingmall.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import study.shoppingmall.domain.Member;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PasswordEncoder passwordEncoder;

    private MockMvc mvc;

//    @BeforeEach
//    public void setUp() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(this.context)
//                .apply(springSecuity())
//                .build();
//    }
    @Test
    public void 회원가입() throws Exception {

        //given
        String email = "member1@naver.com";
        String pw = "qwer123$";
        String name = "김영수";
        String nickname = "오수";
        String gender = "male";
        String birthday = "20202020";
        String phone = "01012345678";
        String zipcode = "02721";
        String address = "서울 성북구 길음로9길 46";

        MemberDto memberDto = new MemberDto(email, pw, name, nickname, gender, birthday, phone, zipcode, address, null);

        Member member = new Member(memberDto);

        memberRepository.save(member);

        //when
        MemberDto memberDto1 = memberService.findMember(member.getId());

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findById(member.getId()).get());

        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member m : members) {
            System.out.println("member.getId() = " + m.getId());
            System.out.println("member.getEmail() = " + m.getEmail());
        }



    }

    //    @Test
//    public void testEntity() {
//        Member member1 = new Member("m1@naver.com", "1", "member1");
//        Member member2 = new Member("m2@naver.com", "2", "member2");
//        Member member3 = new Member("m3@naver.com", "3", "member3");
//        Member member4 = new Member("m4@naver.com", "4", "member4");
//
//        em.persist(member1);
//        em.persist(member2);
//        em.persist(member3);
//        em.persist(member4);
//
//        em.flush();
//        em.clear();
//
//        List<Member> members = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//
//        for (Member member : members) {
//            System.out.println("member.getId() = " + member.getId());
//            System.out.println("member.getEmail() = " + member.getEmail());
//        }
//    }

    //    @Test
//    public void 중복_이메일_예외() throws Exception {
//        //given
//        Member member1 = new Member("m1@naver.com", "1", "member1");
//        Member member2 = new Member("m1@naver.com", "2", "member2");
//
//        //when
//        memberService.join(member1);
//
//        //then
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//
//
//    }
}