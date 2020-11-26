package com.jigi.service;

import com.jigi.domain.Category.Category;
import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Category.CategoryRepository;
import com.jigi.domain.Post.Post;
import com.jigi.domain.Post.PostRepository;
import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.*;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void Post_저장한다(){
        //given
        String oauthId = "1500000011";
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .studentId(123123L)
                .name("test")
                .email("test")
                .oauthId(oauthId)
                .build();
        User user= userRequestDto.toEntity();
        userRepository.save(user);

        String title="java의세계로";
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title(title)
                .content("오세요")
                .endDate(LocalDate.of(2020,11,30))
                .personLimit(5)
                .rawCategoryEnum("STUDY")
                .studentId(123123L)
                .build();
        //when
        postService.insert(postRequestDto, oauthId);

        //then
        List<PostListResponseDto> list = postService.findPostsByCategory("STUDY");
        log.info("사이즈:"+list.size());
        assertThat(list.stream().anyMatch(postListResponseDto
                -> postListResponseDto.getTitle().equals(title))).isEqualTo(true);
    }

    @Test
    public void 카테고리로_Post_조회(){
        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .studentId(100100L)
                .name("test")
                .email("test")
                .build();
        User user= userRequestDto.toEntity();
        userRepository.save(user);

        Category category = categoryRepository.findByCategoryEnum(CategoryEnum.STUDY);
        String title="스터디팀원구합니다";
        Post post = Post.builder()
                .title(title)
                .content("오세요")
                .endDate(LocalDate.of(2020,11,30))
                .personLimit(5)
                .category(category)
                .host(user)
                .build();
        postRepository.save(post);

        //when
        List<PostListResponseDto> list = postService.findPostsByCategory("STUDY");

        //then
        log.info("사이즈:"+list.size());
        assertThat(list.stream().anyMatch(postListResponseDto
                -> postListResponseDto.getTitle().equals(title))).isEqualTo(true);
    }
}
