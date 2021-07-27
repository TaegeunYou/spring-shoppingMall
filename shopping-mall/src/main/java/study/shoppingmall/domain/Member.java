package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shoppingmall.controller.MemberDto;
import study.shoppingmall.domain.coupon.Coupon;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue //strategy 추가해주기
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "member_name")
    private String name;    //API마다 스펙이 다르므로 Dto에서 조건 걸어주기

    @Column(name = "member_nickname")
    private String nickname;

    @Column(name = "member_phone")
    private String phone;

    @Column(name = "member_birthday")
    private LocalDateTime birthday;

    @Column(name = "member_gender")
    private String gender;

    @Column(name = "member_joindate")
    private LocalDateTime joinDate;

    @Column(name = "member_grade")
    private String grade;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

//    @OneToOne
//    private List<Coupon> couponList = new ArrayList<>();  //미구현

    //==생성 메서드==//
    //테스트 용도
    public Member(String email, String pw, String name) {
        this.email = email;
        this.pw = pw;
        this.name = name;
    }
    //dto -> entity
    public Member(MemberDto memberDto) {
        this.email = memberDto.getEmail();
        this.pw = memberDto.getPw();
        this.name = memberDto.getName();
        this.nickname = memberDto.getNickname();
        this.nickname = getNickname();
        this.phone = getPhone();
        this.birthday = getBirthday();
        this.gender = getGender();
        this.address = getAddress();
    }
}
