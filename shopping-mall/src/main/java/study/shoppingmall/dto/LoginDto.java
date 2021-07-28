package study.shoppingmall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")    //????????
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")    //??????????
    private String password;
}
