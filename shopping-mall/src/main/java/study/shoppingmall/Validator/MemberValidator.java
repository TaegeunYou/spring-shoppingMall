package study.shoppingmall.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.repository.MemberRepository;

@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MemberDto memberDto = (MemberDto) obj;

        if(!memberRepository.findByEmail(memberDto.getEmail()).isEmpty()) {
            errors.rejectValue("email", "중복이메일", "이미 존재하는 이메일입니다.");
        }

        if (!memberDto.getPw().equals(memberDto.getPwc())) {
            errors.rejectValue("pwc", "비밀번호불일치", "비밀번호를 확인해 주세요.");
        }

        if(!memberRepository.findByNickname(memberDto.getNickname()).isEmpty()) {
            errors.rejectValue("nickname", "중복닉네임", "이미 존재하는 닉네임입니다.");
        }
    }
}
