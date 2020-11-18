package com.jigi.service;

import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.PostListResponseDto;
import com.jigi.web.dto.PostRequestDto;
import com.jigi.web.dto.PostResponseDto;
import com.jigi.domain.Post.Post;
import com.jigi.domain.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Log
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void insert(PostRequestDto postRequestDto)throws IllegalArgumentException{
        Post post = postRequestDto.toEntity();


        log.info("제목:"+ postRequestDto.getTitle());

        User host = userRepository.findByStudentId(postRequestDto.getStudentId()).orElseThrow(()->new IllegalArgumentException("유효하지 않는 회원입니다."));
        post.setHost(host);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto find(Long id)throws IllegalArgumentException{
        Post post =postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾는 post가 없음"));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findPostsByCategory(String category) {
        List<PostListResponseDto> lists = postRepository.findAllByCategoryEnumDesc(CategoryEnum.valueOf(category.toUpperCase())).stream()
                .map(PostListResponseDto::new)
                .collect(
                        Collectors.toList());
        return lists;
    }

    @Transactional
    public void delete(Long id) throws IllegalArgumentException{
        postRepository.deleteById(id);
    }
}
