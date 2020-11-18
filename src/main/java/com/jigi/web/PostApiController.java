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
@RestController("/api/v1/post/**")
public class PostApiController {
    private final PostService postService;

    @PostMapping("/")
    public void insert(@RequestBody PostRequestDto dto){
        postService.insert(dto);
    }

    @GetMapping("/list/{category}")
    public List<PostListResponseDto> findPostsByCategory(@PathVariable String category){
        return postService.findPostsByCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> find(@PathVariable Long id){
        ResponseEntity responseEntity=null;
        try{
            responseEntity = new ResponseEntity(postService.find(id), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        ResponseEntity responseEntity=null;
        try{
            postService.delete(id);
            responseEntity = new ResponseEntity("ok", HttpStatus.OK);
        }catch (IllegalArgumentException e){
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return responseEntity;
    }
}
