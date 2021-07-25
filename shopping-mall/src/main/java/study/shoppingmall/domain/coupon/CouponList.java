package study.shoppingmall.domain.coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shoppingmall.domain.Member;

import javax.persistence.*;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponList {

    @Id @GeneratedValue
    @Column(name = "coupon_list_id")
    private Long id;

    @OneToOne
    @JoinColumn()
    private Member member;
}
