package com.jjangiji.hankkimoa.member.controller;

import com.jjangiji.hankkimoa.common.auth.JwtTokenProvider;
import com.jjangiji.hankkimoa.member.domain.Member;
import com.jjangiji.hankkimoa.member.domain.SocialType;
import com.jjangiji.hankkimoa.member.dto.AccessTokenDto;
import com.jjangiji.hankkimoa.member.dto.KakaoProfileDto;
import com.jjangiji.hankkimoa.member.dto.RedirectDto;
import com.jjangiji.hankkimoa.member.service.KakaoService;
import com.jjangiji.hankkimoa.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, KakaoService kakaoService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.kakaoService = kakaoService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestBody RedirectDto redirectDto){
        AccessTokenDto accessTokenDto = kakaoService.getAccessToken(redirectDto.getCode());
        KakaoProfileDto kakaoProfileDto  = kakaoService.getKakaoProfile(accessTokenDto.getAccess_token());
        Member originalMember = memberService.getMemberBySocialId(kakaoProfileDto.getId());
        if(originalMember == null){
            originalMember = memberService.createOauth(kakaoProfileDto.getId(), SocialType.KAKAO);
        }
        String jwtToken = jwtTokenProvider.createToken(originalMember.getSocialId(), originalMember.getRole().toString());

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", originalMember.getId());
        loginInfo.put("token", jwtToken);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
}
