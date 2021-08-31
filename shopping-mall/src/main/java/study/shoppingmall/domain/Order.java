package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "detail")
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   //Order와 Order의 라이프 사이클이 같다.
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //ORDER, CANCEL

    //==setter==//
    protected void setStatus(OrderStatus status) {
        this.status = status;
    }

    //==연관관계 메서드==//
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.UpdateOrder(this);
    }

    //==생성 메서드==//
    protected Order(String receiverName, String receiverPhone, String detail, Member member, Delivery delivery, OrderStatus status) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.detail = detail;
        this.member = member;
        this.delivery = delivery;
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }

    public static Order createOrder(String receiverName, String receiverPhone, String detail,
                                    Member member, Delivery delivery, List<OrderProduct> orderProducts) {
        Order order = new Order(receiverName, receiverPhone, detail, member, delivery, OrderStatus.ORDER);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        return order;
    }

    //==비즈니스 로직==//
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

    //==조회 로직==// (전체 주문 가격 조회)
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }
}
