package study.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.shoppingmall.domain.Member;
import study.shoppingmall.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */

    @Transactional
    public Long join(Member member) {
        validateDuplicateEmail(member);     //중복 이메일 검증
        validateDuplicateNickname(member);  //중복 닉네임 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateEmail(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    private void validateDuplicateNickname(Member member) {
        List<Member> findMembers = memberRepository.findByNickname(member.getNickname());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }


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
     * 엔티티말고 MemberForm 클래스에 맞추기
     */

//    @Transactional
//    public void update(Long id, MemberDto) {    //변경 감지 이용
//        MemberDto memberDto
//    }
}


















