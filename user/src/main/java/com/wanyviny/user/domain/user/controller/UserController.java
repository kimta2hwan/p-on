package com.wanyviny.user.domain.user.controller;

import com.wanyviny.user.domain.common.BasicResponse;
import com.wanyviny.user.domain.user.PRIVACY;
import com.wanyviny.user.domain.user.dto.KakaoUserDto;
import com.wanyviny.user.domain.user.dto.UserDto;
import com.wanyviny.user.domain.user.dto.UserSignUpDto;
import com.wanyviny.user.domain.user.entity.User;
import com.wanyviny.user.domain.user.repository.UserRepository;
import com.wanyviny.user.domain.user.service.UserService;
import com.wanyviny.user.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/kakao-profile")
    public ResponseEntity<BasicResponse> getKakaoProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        Long id = Long.parseLong(authentication.getName());
        User userProfile = userService.getUserProfile(id);

        KakaoUserDto kakaoUserDto = KakaoUserDto.builder()
                .profileImage(userProfile.getProfileImage())
                .nickName(userProfile.getNickname())
                .privacy(userProfile.getPrivacy())
                .build();

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("카카오에서 받은 유저 정보 조회 성공")
                .count(1)
                .result(Collections.singletonList(kakaoUserDto))
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @RequestMapping("/sign-up")
    public ResponseEntity<BasicResponse> signup(@RequestBody UserSignUpDto userSignUpDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        userService.signUp(userSignUpDto, Long.parseLong(authentication.getName()));

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원 가입 성공!")
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @RequestMapping("/profile")
    public ResponseEntity<BasicResponse> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        Long id = Long.parseLong(authentication.getName());
        User userProfile = userService.getUserProfile(id);

        UserDto userDto = UserDto.builder()
                .id(userProfile.getId())
                .profileImage(userProfile.getProfileImage())
                .nickName(userProfile.getNickname())
                .privacy(userProfile.getPrivacy())
                .stateMessage(userProfile.getStateMessage())
                .build();

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("유저 정보 조회 성공")
                .count(1)
                .result(Collections.singletonList(userDto))
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @RequestMapping("/update")
    public ResponseEntity<BasicResponse> userUpdate(@RequestBody UserDto userDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        Long id = Long.parseLong(authentication.getName());

        userService.update(userDto, Long.parseLong(authentication.getName()));

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("유저 정보 수정 성공")
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
}