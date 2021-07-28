package study.shoppingmall;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Address;
import study.shoppingmall.domain.Member;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.MemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitMember initMember;
    private final MemberRepository memberRepository;
    private MemberService memberService;

    @PostConstruct
    public void init() {
        initMember.initMember();
        initMember.initAdmin();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitMember {

        private final EntityManager em;

        public void initMember() {
            String email = "abc@naver.com";
            String pw = "qwer123$";
            String name = "홍길동";
            String nickname = "멋쟁이";
            String gender = "male";
            String birthday = "20202020";
            String phone = "01012345678";
            String zipcode = "02721";
            String address = "서울 성북구 길음로9길 46";


            MemberDto memberDto = new MemberDto(email, pw, name, nickname, gender, birthday, phone, zipcode, address, null);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            Member member = new Member(memberDto);
            em.persist(member);

        }

        public void initAdmin() {
            String email = "admin@example.com";
            String pw = "qwer123$";
            String name = "관리자";
            String nickname = "자리관";
            String gender = "female";
            String birthday = "10101010";
            String phone = "01087654321";
            String zipcode = "12345";
            String address = "대전광역시";


            MemberDto memberDto = new MemberDto(email, pw, name, nickname, gender, birthday, phone, zipcode, address, null);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            Member member = new Member(memberDto);
            em.persist(member);
        }
    }
}
