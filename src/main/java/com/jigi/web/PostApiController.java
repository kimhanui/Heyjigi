package com.jigi.web;


import com.jigi.service.PostService;
import com.jigi.web.dto.PostListResponseDto;
import com.jigi.web.dto.PostRequestDto;
import com.jigi.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @PostMapping("/api/v1/post") //ajax응답 void로 해주면 FE반응없음.
    public Long insert(@RequestBody PostRequestDto dto) {
        log.info("hello");
        return postService.insert(dto);
    }

    @GetMapping("/api/v1/post/list/{category}")
    public ResponseEntity<List<PostListResponseDto>> findPostsByCategory(@PathVariable String category) {
        List<PostListResponseDto> list = postService.findPostsByCategory(category);
        log.info("list[0]:" + list.get(0).getTitle());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/api/v1/post/{id}")
    public ResponseEntity<PostResponseDto> find(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = new ResponseEntity(postService.find(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            postService.delete(id);
            responseEntity = new ResponseEntity("ok", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }
}
