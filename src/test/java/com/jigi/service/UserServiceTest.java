package com.jigi.service;

import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.UserRequestDto;
import com.jigi.web.dto.UserResponseDto;
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
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    public void findAll(){
        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .studentId(100100L)
                .name("test")
                .contact("test")
                .build();
        User user= userRequestDto.toEntity();
        userRepository.save(user);
        //when
        List<UserResponseDto> list =userService.findAll();

        //then
        log.info(Integer.toString(list.size()));
        assertThat(list.size()).isGreaterThan(0);

    }
}
