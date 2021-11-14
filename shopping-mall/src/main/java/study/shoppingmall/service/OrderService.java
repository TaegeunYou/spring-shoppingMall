package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.*;
import study.shoppingmall.dto.CartDto;
import study.shoppingmall.dto.DeliveryDto;
import study.shoppingmall.repository.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;


    /**
     * 주문
     */
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

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), cartDto.getCount());
            orderProducts.add(orderProduct);

            //재고 감소
            product.removeStock(cartDto.getCount());
        }

        //주문 생성
        Order order = Order.createOrder(receiverName, receiverPhone, detail, member, delivery, orderProducts);

        //주문 저장
        orderRepository.save(order);    //delivery, orderProduct 은 cascade

        return order.getId();

    }
    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId).get();
        //주문 취소
        orderRepository.delete(order);
        order.cancel();
    }

    /**
     * 모든 주문 목록
     */
    public List<DeliveryDto> findALlOrdersWithDelivery() {
        List<Order> orders = orderRepository.findAll();
        List<DeliveryDto> deliveryDtos = new ArrayList<>();

        for (Order order : orders) {
            Member member = order.getMember();
            List<OrderProduct> orderProducts = order.getOrderProducts();
            Delivery delivery = order.getDelivery();

            for (OrderProduct orderProduct : orderProducts) {
                orderProduct.getProduct().getName();
                orderProduct.getCount();
                DeliveryDto deliveryDto = new DeliveryDto(order.getId(), order.getCreatedDate(),
                        order.getDetail(), member.getName(),
                        member.getEmail(), delivery.getStatus(),
                        orderProduct.getProduct().getName(),  orderProduct.getCount());

                deliveryDtos.add(deliveryDto);
            }

        }
        return deliveryDtos;
    }


    /**
     * 회원의 주문 목록
     */
    public List<DeliveryDto> findOrders(Long memberId) {

        List<Order> orders = orderRepository.findByMemberId(memberId);
        List<DeliveryDto> deliveryDtoList = new ArrayList<>();

        for (Order order : orders) {
            Delivery delivery = order.getDelivery();
            List<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts) {
                DeliveryDto deliveryDto = new DeliveryDto(order.getOrderDate(), orderProduct.getProduct().getName(), orderProduct.getCount(), orderProduct.getOrderPrice(), delivery.getStatus(), order.getDetail());
                deliveryDtoList.add(deliveryDto);
            }
        }

        return deliveryDtoList;
    }
}












