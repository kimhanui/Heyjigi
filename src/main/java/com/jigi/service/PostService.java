package com.jigi.service;

import com.jigi.domain.Category.Category;
import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Category.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long insert(PostRequestDto postRequestDto, String oauthId)throws IllegalArgumentException{

        Category category = categoryRepository.findByCategoryEnum(CategoryEnum.valueOf(postRequestDto.getRawCategoryEnum().toUpperCase()));
        Post post = postRequestDto.toEntity(category);
        User host = userRepository.findByOauthId(oauthId)
                .orElseThrow(()->new IllegalArgumentException("유효하지 않는 회원입니다."));
        post.setHost(host);
        return postRepository.save(post).getId();
    }
    @Transactional(readOnly = true)
    public User getHost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        return post.getHost();
    }
    @Transactional
    public Long getParticipant(Long id, String oauthId)throws IllegalArgumentException{
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        User user = userRepository.findByOauthId(oauthId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        post.addGuest(user);
        return id;
    }

    @Transactional
    public Long putParticipant(Long id, String oauthId)throws IllegalArgumentException{
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        User user = userRepository.findByOauthId(oauthId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        post.deleteGuest(user);
        return id;
    }

    @Transactional(readOnly = true)
    public PostResponseDto find(Long id)throws IllegalArgumentException{
        Post post =postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findPostsByCategory(String category) {
        List<PostListResponseDto> lists = postRepository.findAllByCategoryEnumDesc(CategoryEnum.valueOf(category.toUpperCase())).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return lists;
    }

    @Transactional
    public Long update(Long id, PostRequestDto postRequestDto)throws IllegalArgumentException
    {
        Category category = categoryRepository.findByCategoryEnum(CategoryEnum.valueOf(postRequestDto.getRawCategoryEnum().toUpperCase()));
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        post.update(postRequestDto, category);
        return id;
    }

    @Transactional
    public Long delete(Long id) throws IllegalArgumentException{
        postRepository.deleteById(id);
        return id;
    }
}
