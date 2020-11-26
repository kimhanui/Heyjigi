package com.jigi.web;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.jigi.service.MailService;
import com.jigi.service.PostService;
import com.jigi.service.UserService;
import com.jigi.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    private final UserService userService;
    private final HttpSession httpSession;
    private final MailService mailService;

    /**
     * Post 등록되면
     * 회원들에게 알림간다. -> userRepository
     */
    @PostMapping("/api/v1/post") //ajax응답 void로 해주면 FE반응없음.
    public ResponseEntity<String> insert(@RequestBody PostRequestDto dto) throws HttpMessageNotReadableException {//}, @AuthenticationPrincipal OAuth2User oAuth2User) {
        ResponseEntity<String> responseEntity = null;
        try {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
            Long id = postService.insert(dto, sessionUser.getOauthId());
            responseEntity = new ResponseEntity(id, HttpStatus.OK);
        } catch (NullPointerException e)//dto에 값 다 채우지 않음
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        /** 알림 보내기 **/
        List<UserResponseDto> userList = userService.findByCategoryEnum(dto.getRawCategoryEnum());
        for (UserResponseDto userResponseDto : userList) {
            log.info("보낼대상:" + userResponseDto.getName());
        }
        List<MailDto> mailDtos = userList.stream().map(MailDto::new).collect(Collectors.toList());//메일 보내기
        for (MailDto mailDto : mailDtos) {
            mailDto.setType(1);
            mailService.mailSend(mailDto);
        }
        return responseEntity;
    }

    @PutMapping("/api/v1/post/join/{id}")
    public Long join(@PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
//        /** 알림 보내기 **/
//        List<UserResponseDto> userList = userService.findByCategoryEnum(dto.getRawCategoryEnum());
//        for (UserResponseDto userResponseDto : userList) {
//            log.info("보낼대상:" + userResponseDto.getName());
//        }
//        List<MailDto> mailDtos = userList.stream().map(MailDto::new).collect(Collectors.toList());//메일 보내기
//        for (MailDto mailDto : mailDtos) {
//            mailDto.setType(1);
//            mailService.mailSend(mailDto);
//        }

        return postService.getParticipant(id, sessionUser.getOauthId());
    }

    @PutMapping("/api/v1/post/drop/{id}")
    public Long drop(@PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        return postService.putParticipant(id, sessionUser.getOauthId());
    }

    @GetMapping("/api/v1/post/list/{category}")
    public ResponseEntity<List<PostListResponseDto>> findPostsByCategory(@PathVariable String category) {
        List<PostListResponseDto> list = postService.findPostsByCategory(category);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/v1/post/{id}")
    public ResponseEntity<PostResponseDto> find(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity(postService.find(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PutMapping("/api/v1/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) throws HttpMessageNotReadableException {
        log.info("---post update:" + postRequestDto.toString());
        return postService.update(id, postRequestDto);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity(postService.delete(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }
}
