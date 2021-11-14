package study.shoppingmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.shoppingmall.domain.Address;

@Data
@NoArgsConstructor
public class OrderDto {

//    private String buyerName;
//
//    private String buyerEmail;
//
//    private String phone;

    private String receiverName;

    private String receiverPhone;

    private String zipcode;
    private String address;
    private String addressdetail;

    private String detail;

    public OrderDto(String receiverName, String receiverPhone, String zipcode, String address, String addressdetail) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressdetail = addressdetail;
    }




}
