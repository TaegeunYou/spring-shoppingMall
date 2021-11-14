package study.shoppingmall.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {

    private Long id;

    private String productName;

    private int price;

    private int count;

    private int totalPrice;

    public CartDto(Long id, String productName, int price, int count) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.totalPrice = price * count;
    }
}
