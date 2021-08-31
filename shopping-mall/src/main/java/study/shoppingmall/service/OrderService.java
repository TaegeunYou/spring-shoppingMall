package study.shoppingmall.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.*;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.repository.CartRepository;
import study.shoppingmall.repository.MemberRepository;
import study.shoppingmall.repository.OrderRepository;
import study.shoppingmall.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;


    /**
     * 주문
     */

    //하나의 상품에 대한 주문 (여러 상품을 주문할 경우 이 메서드를 여러번 호출?)
//    @Transactional
//    public Long order(Long memberId, Address address, OrderProductParam... orderProductParams) {   //Long productId, int count
//
//
//        //엔티티 조회
//        Member member = memberRepository.findById(memberId).get();
//
//        //배송정보 생성
//        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
//
//        //주문상품 생성
//        List<OrderProduct> orderProducts = new ArrayList<>();
//
//        for (OrderProductParam orderProductParam : orderProductParams) {
//            Product product = productRepository.findById(orderProductParam.getProductId()).get();
//            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), orderProductParam.getCount());
//            orderProducts.add(orderProduct);
//        }
//
//        //주문 생성
//        Order order = Order.createOrder(member, delivery, orderProducts);
//
//        //주문 저장
//        orderRepository.save(order);    //delivery, orderProduct 은 cascade
//
//        return order.getId();
//
//    }

    @Transactional
    public Long order(String receiverName, String receiverPhone, String detail,
                      Long memberId, Address address, List<CartDto> cartList) {   //Long productId, int count

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();

        //배송정보 생성
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);

        //주문상품 생성
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CartDto cartDto : cartList) {

            //cartDto -> cart -> product
            Product product = cartRepository.findById(cartDto.getId()).get().getProduct();

            //
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), cartDto.getCount());
            orderProducts.add(orderProduct);
        }

        //주문 생성
        Order order = Order.createOrder(receiverName, receiverPhone, detail, member, delivery, orderProducts);

        //주문 저장
        orderRepository.save(order);    //delivery, orderProduct 은 cascade

        return order.getId();

    }

    @Getter
    public static class OrderProductParam {
        private Long productId;
        private int count;
    }

    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).get();
        //주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     */

//    public List<Order> findOrders(OrderSerarch orderSerarch) {
//        return orderRepository.findAll(orderSerarch);
//    }

    /**
     * 장바구니 담기
     */
    @Transactional
    public void addCart() {

    }
}












