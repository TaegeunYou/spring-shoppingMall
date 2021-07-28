package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.domain.Member;
import study.shoppingmall.domain.Role;
import study.shoppingmall.repository.MemberRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */

//    @Transactional
//    public Long join(Member member) {
//        validateDuplicateEmail(member);     //중복 이메일 검증
//        validateDuplicateNickname(member);  //중복 닉네임 검증
//        memberRepository.save(member);
//        return member.getId();
//    }

    @Transactional
    public Long join(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        Member member = new Member(memberDto);


        memberRepository.save(member);
        return member.getId();
    }

//    private void validateDuplicateEmail(Member member) {
//        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
//        if(!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        }
//    }
//
//    private void validateDuplicateNickname(Member member) {
//        List<Member> findMembers = memberRepository.findByNickname(member.getNickname());
//        if(!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
//        }
//    }


    /**
     * 회원조회
     *
     * question1: 회원이 비어있는 경우 잘 처리가 될지?
     */

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    /**
     * 회원 정보 수정
     *
     * 엔티티말고 MemberDto 클래스에 맞추기
     */

//    @Transactional
//    public void update(Long id, MemberDto) {    //변경 감지 이용
//        MemberDto memberDto
//    }

    /**
     * 회원가입 폼
     */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();  //key: dto(필드), value: 작성한 message

        for (FieldError error : errors.getFieldErrors()) {  //실패한 필드 목록을 가져옴
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    //로그인시 인증을 위한 메서드
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Member> userEntityWrapper = memberRepository.findOptionalByEmail(userEmail);
        Member userEntity = userEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        //롤을 부여
        if (("admin@example.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(userEntity.getEmail(), userEntity.getPw(), authorities);
    }
}


















