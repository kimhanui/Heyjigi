package com.jigi.repository;

import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.UserRequestDto;
import lombok.extern.java.Log;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void findByOauthId_성공한다(){
        //given
        String name = "test";
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .studentId(120120L)
                .name(name)
                .email("test")
                .oauthId("1500000009")
                .build();
        User user= userRequestDto.toEntity();
        userRepository.save(user);

        //when
        Optional<User> target =userRepository.findByOauthId(user.getOauthId());

        //then
        assertThat(target.get().getName()).isEqualTo(name);
    }
}
