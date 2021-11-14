package study.shoppingmall.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import study.shoppingmall.domain.Cart;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.CartRepository;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class CommentTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @Test
    public void 상품평() throws Exception {

        //given
        Long memberId = new Long(1);

        //when
        cartService.addCart(memberId, new Long(3), "abc", 3);

        Long memberId2 = cartRepository.findByMemberId(memberId).get(0).getMember().getId();

        //then
        Assertions.assertThat(memberId).isEqualTo(memberId2);



        List<Cart> carts = em.createQuery("select c from Cart c", Cart.class)
                .getResultList();

        for (Cart c : carts) {
            System.out.println("member.getId() = " + c.getId());
        }



    }

}