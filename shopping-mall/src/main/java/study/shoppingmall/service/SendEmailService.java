package study.shoppingmall.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Member;
import study.shoppingmall.dto.MailDto;
import study.shoppingmall.repository.MemberRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SendEmailService {

    private final MemberRepository memberRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "dbxorms2310@naver.com";

    /**
     * 발송할 메일 생성 및 설정
     */
    @Transactional
    public MailDto createMailAndChangePassword(String userEmail, String userName) {
        String password = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userName+"님의 국민가구 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 국민가구 임시비밀번호 안내 관련 이메일 입니다. [" + userName + "] 님의 임시 비밀번호는 "+ password +" 입니다.");
        updatePassword(password, userEmail);
        return dto;
    }

    /**
     * 발급된 비밀번호로 회원정보 수정
     */
    @Transactional
    public void updatePassword(String password, String userEmail) {

        Member findMember = memberRepository.findByEmail(userEmail).get(0);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        findMember.changePassword(passwordEncoder.encode(password));

    }

    /**
     * 임시 비밀번호 발급
     */
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    /**
     * 메일 발송
     */
    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
