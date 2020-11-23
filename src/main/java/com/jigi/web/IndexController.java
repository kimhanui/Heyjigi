package com.jigi.web;

import com.jigi.service.PostService;
import com.jigi.service.UserService;
import com.jigi.web.dto.My.MyResponseDto;
import com.jigi.web.dto.PostListResponseDto;
import com.jigi.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Log
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) { //서버 템플릿엔진(mustache)에서 사용가능한 객체 저장,전달
        List<PostListResponseDto> list = postService.findPostsByCategory("arduino");
        model.addAttribute("posts", list);
        log.info("post list size=" + list.size());
        return "home";
    }


    @GetMapping("/post/find/{id}")
    public String post_find(@PathVariable Long id, Model model) {
        PostResponseDto postResponseDto = postService.find(id);
        model.addAttribute("post", postResponseDto);
        return "post-find";
    }

    @GetMapping("/post/save")
    public String post_save(Model model) {
        return "post-save";
    }

    @GetMapping("/my/{studentId}")
    public String mypage(@PathVariable Long studentId, Model model) {
        //내 계정 정보필요
        MyResponseDto myResponseDto = userService.findMyInfo(studentId);
        log.info(myResponseDto.toString());
        model.addAttribute("my", myResponseDto);
        return "my";
    }
}
