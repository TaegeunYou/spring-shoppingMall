package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.dto.MemberDto;
import study.shoppingmall.domain.Member;
import study.shoppingmall.domain.Role;
import study.shoppingmall.repository.MemberRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        Member member = new Member(memberDto);
        memberRepository.save(member);

        return member.getId();
    }


    /**
     * 모든 회원 조회
     */
    public List<MemberDto> findAllMembers() {

        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();

        for (Member member : members) {
            MemberDto memberDto = new MemberDto(member);
            memberDtos.add(memberDto);
        }

        return memberDtos;
    }

    /**
     * 회원 한명 조회
     */
    public MemberDto findMember(Long id) {
        Member member = memberRepository.findById(id).get();
        MemberDto memberDto = new MemberDto(member);
        return memberDto;
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public void update(MemberDto memberDto) {    //변경 감지 이용
        Member findMember = memberRepository.findByEmail(memberDto.getEmail()).get(0);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        findMember.changeMember(memberDto);
    }

    /**
     * 로그인 시 아이디를 확인하여 권한 부여
     */
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

    /**
     * 해당 섹션에 있는 회원정보 조회
     */
    public Long findMyId() {

        //세션 객체 안에 있는 ID정보 저장
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = auth.getName(); //get logged in username

        Member member = memberRepository.findByEmail(memberEmail).get(0);

        return member.getId();
    }

    /**
     * 회원 id를 통해 닉네임 찾기
     */
    public String findNickname(Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getNickname();
    }

    /**
     * 회원 id를 통해 Member Dto 반환
     */
    public MemberDto memberIdToDto() {

       Member member = memberRepository.getById(findMyId());

       return new MemberDto(member);
    }

    /**
     * 비밀번호 찾기
     */
    public boolean memberEmailCheck(String userEmail, String userName) {
        Optional<Member> member = memberRepository.findOptionalByEmail(userEmail);
        if(!(member.isEmpty()) && member.get().getName().equals(userName)) {
            return true;
        } else {
            return false;
        }


    }
}


















