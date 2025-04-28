package com.jjangiji.hankkimoa.member.service;

import com.jjangiji.hankkimoa.member.domain.Member;
import com.jjangiji.hankkimoa.member.domain.SocialType;
import com.jjangiji.hankkimoa.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberBySocialId(String socialId){
        Member member = memberRepository.findBySocialId(socialId).orElse(null);
        return member;
    }

    public Member createOauth(String socialId, SocialType socialType){
        Member member = Member.builder()
                .socialType(socialType)
                .socialId(socialId)
                .build();
        memberRepository.save(member);
        return member;
    }

}
