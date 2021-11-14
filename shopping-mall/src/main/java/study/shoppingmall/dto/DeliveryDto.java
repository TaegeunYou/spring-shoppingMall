package study.shoppingmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.shoppingmall.domain.DeliveryStatus;
import study.shoppingmall.domain.Order;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DeliveryDto {

    //Order Entity
    private Long id;
    private LocalDateTime orderDate;

    //OrderProduct Entity
    private String productName;
    private int productCount;
    private int productPrice;

    //Delivery Entity
    private DeliveryStatus deliveryStatus;
    private String orderDetail;

    //Member Entity
    private String memberName;
    private String memberEmail;

    public DeliveryDto(LocalDateTime orderDate, String productName, int productCount, int productPrice, DeliveryStatus deliveryStatus, String orderDetail){
        this.orderDate = orderDate;
        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.deliveryStatus = deliveryStatus;
        this.orderDetail = orderDetail;
    }


    public DeliveryDto(Long id, LocalDateTime orderDate, String orderDetail,
                                       String memberName, String memberEmail, DeliveryStatus deliveryStatus,
                                       String productName, int productCount) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderDetail = orderDetail;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.deliveryStatus = deliveryStatus;
        this.productName = productName;
        this.productCount = productCount;
    }
}
