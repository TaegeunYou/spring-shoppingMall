package study.shoppingmall.dto;

import lombok.*;
import study.shoppingmall.domain.Address;
import study.shoppingmall.domain.Member;

import javax.persistence.Embedded;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
//    @Size(min = 8, max = 20, message = "8~20자로 입력해 주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문/숫자/특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자여야 합니다.")
    private String pw;

//    @NotBlank(message = "확인을 위해 비밀번호를 입력해 주세요.")
    private String pwc;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    @NotBlank(message = "성별을 입력해 주세요")
    private String gender;

    @NotBlank(message = "생년월일을 입력해 주세요.")
//    @Size(min = 8, max = 8, message = "생년월일을 올바르게 입력해 주세요.")
    private String birthday;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phone;

    @NotBlank(message = "우편번호를 입력해 주세요.")
    private String zipcode;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

    private String addressdetail;

    private LocalDateTime createdDate;


//    @Embedded
//    private Address address;

//    public MemberDto(String email, String pw, String pwc, String name, String nickname, String gender, String birthday, String phone) {
//        this.email = email;
//        this.pw = pw;
//        this.pwc = pwc;
//        this.name = name;
//        this.nickname = nickname;
//        this.gender = gender;
//        this.birthday = birthday;
//        this.phone = phone;
//    }

//    public MemberDto(String email, String pw, String name, String nickname, String gender, String phone) {
//        this.email = email;
//        this.pw = pw;
//        this.name = name;
//        this.nickname = nickname;
//        this.gender = gender;
//        this.phone = phone;
//    }

    //==생성 메서드==//

    public MemberDto(String email, String pw, String name, String nickname, String gender, String birthday, String phone, String zipcode, String address) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
    }
    public MemberDto(String email, String pw, String name, String nickname, String gender, String birthday, String phone, String zipcode, String address, String addressdetail) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressdetail = addressdetail;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.pw = member.getPw();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.birthday = member.getBirthday();
        this.phone = member.getPhone();
        this.zipcode = member.getAddress().getZipcode();
        this.address = member.getAddress().getAddress();
        this.addressdetail = member.getAddress().getAddressdetail();
        this.createdDate = member.getCreatedDate();
    }


    public MemberDto(Long Id) {

    }

    //==Setter==//
    public void setPw(String pw) {
        this.pw = pw;
    }
}
