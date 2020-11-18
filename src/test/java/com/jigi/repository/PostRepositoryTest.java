package com.jigi.repository;

import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Post.Post;
import com.jigi.domain.Post.PostRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void 카테고리로_Post_조회() {
        //given(data.sql)

        //when
        List<Post> list = postRepository.findAllByCategoryEnumDesc(CategoryEnum.ARDUINO);

        //then
        assertThat(list.get(0).getTitle()).isEqualTo("부품공구합시다");
    }
}
