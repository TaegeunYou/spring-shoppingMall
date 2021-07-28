package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String zipcode;
    private String address;
    private String addressdetail;

    public Address(String zipcode, String address) {
        this.zipcode = zipcode;
        this.address = address;
        this.addressdetail = null;
    }

    public Address(String zipcode, String address, String addressdetail) {
        this.zipcode = zipcode;
        this.address = address;
        this.addressdetail = addressdetail;
    }
}
