package study.shoppingmall.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Address;
import study.shoppingmall.domain.Order;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.repository.CartRepository;
import study.shoppingmall.repository.OrderRepository;
import study.shoppingmall.service.CartService;
import study.shoppingmall.service.OrderService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class OrderTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartRepository cartRepository;


    @Test
    public void 주문()  {

        //given
        Long memberId = new Long(1);

        cartService.addCart(memberId, new Long(3), "abc", 3);

        CartDto cartDto = new CartDto(cartRepository.findByMemberId(memberId).get(0).getId(), "상품1", 10000, 10);
        List<CartDto> cartDtoList = new ArrayList<>();
        cartDtoList.add(cartDto);

        //when
        orderService.order("홍길동", "01023456789", "부재시 경비실에 맡겨주세요."
                , memberId, new Address("12345", "서울광역시", "서울역"), cartDtoList);

        Order order = orderRepository.findByMemberId(memberId).get(0);

        //then
        Assertions.assertThat(order.getMember().getName()).isEqualTo("홍길동");



        List<Order> orders = em.createQuery("select m from Order m", Order.class)
                .getResultList();

        for (Order o : orders) {
            System.out.println("member.getId() = " + o.getId());
            System.out.println("member.getEmail() = " + o.getReceiverName());
        }
    }
}