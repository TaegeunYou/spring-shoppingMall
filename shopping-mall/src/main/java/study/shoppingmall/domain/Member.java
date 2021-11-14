package study.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shoppingmall.dto.MemberDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue //strategy 추가해주기
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_pw")
    private String pw;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_nickname")
    private String nickname;

    @Column(name = "member_phone")
    private String phone;

    @Column(name = "member_birthday")
    private String birthday;

    @Column(name = "member_gender")
    private String gender;

    @Column(name = "member_grade")
    private String grade;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

//    @OneToOne
//    private List<Coupon> couponList = new ArrayList<>();  //미구현

    //==생성 메서드==//
    //dto -> entity
    public Member(MemberDto memberDto) {
        this.email = memberDto.getEmail();
        this.pw = memberDto.getPw();
        this.name = memberDto.getName();
        this.nickname = memberDto.getNickname();
        this.gender = memberDto.getGender();
        this.birthday = memberDto.getBirthday();
        this.phone = memberDto.getPhone();
        this.gender = memberDto.getGender();
        this.address = new Address(memberDto.getZipcode(), memberDto.getAddress(), memberDto.getAddressdetail());
        this.grade = "normal";
    }

    public void changeMember(MemberDto memberDto) {
        this.email = memberDto.getEmail();
        this.pw = memberDto.getPw();
        this.name = memberDto.getName();
        this.nickname = memberDto.getNickname();
        this.gender = memberDto.getGender();
        this.birthday = memberDto.getBirthday();
        this.phone = memberDto.getPhone();
        this.gender = memberDto.getGender();
        this.address = new Address(memberDto.getZipcode(), memberDto.getAddress(), memberDto.getAddressdetail());
        this.grade = "normal";
    }

    public void changePassword(String pw) {
        this.pw = pw;
    }
}
