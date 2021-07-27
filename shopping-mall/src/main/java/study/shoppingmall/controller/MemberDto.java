package study.shoppingmall.controller;

import lombok.*;
import study.shoppingmall.domain.Address;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class MemberDto {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 20, message = "8~20자로 입력해 주세요.")
    private String pw;

//    @NotBlank(message = "확인을 위해 비밀번호를 입력해 주세요.")
    private String pwc;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "별명을 입력해 주세요.")
    private String nickname;

    @NotBlank(message = "성별을 입력해 주세요")
    private String gender;

//    @NotBlank(message = "생년월일을 입력해 주세요.")
    private String birthday;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phone;

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

    public MemberDto(String email, String pw, String name, String nickname, String gender, String phone) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.phone = phone;
    }
}
