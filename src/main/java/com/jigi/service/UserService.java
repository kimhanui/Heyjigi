package com.jigi.service;

import com.jigi.domain.Category.Category;
import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Category.CategoryRepository;
import com.jigi.domain.Post.Post;
import com.jigi.domain.Post.PostRepository;
import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.My.MyResponseDto;
import com.jigi.web.dto.UserRequestDto;
import com.jigi.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class UserService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public UserResponseDto find(Long id) throws IllegalArgumentException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAllBystudentIdAsc().stream()
                .map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findByCategoryEnum(String rawCategory) {
        return userRepository.findByCategoryEnum(CategoryEnum.valueOf(rawCategory.toUpperCase()))
                .stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    /**
     * My페이지를 위한 정보조회 메소드
     */
    @Transactional(readOnly = true)
    public MyResponseDto findMyInfoByOauthId(String oauthId) throws IllegalArgumentException {
        User user = userRepository.findByOauthId(oauthId).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));
        log.info("---findMyInfoByOauthId()=" + user.getName() + " " + user.getEmail());
        return new MyResponseDto(user);
    }

    @Transactional
    public void cancel_participated(Long id, String oauthId) {
        User user = userRepository.findByOauthId(oauthId).orElseThrow(() -> new IllegalArgumentException(("찾는 회원이 없습니다.")));
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾는 글이 업습니다."));
        post.deleteGuest(user);
    }

    @Transactional
    public void updateBasic(UserRequestDto userRequestDto) throws IllegalArgumentException {
        log.info("-----updateBasic service=" + userRequestDto.toString());

        User user = userRepository.findByOauthId(userRequestDto.getOauthId())
                .orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));

        user.updateBasic(userRequestDto);
    }

    @Transactional
    public void updateCategory(String oauthId, String rawCategory) throws IllegalArgumentException {
        User user = userRepository.findByOauthId(oauthId).orElseThrow(() -> new IllegalArgumentException("찾는 회원이 없습니다."));
        Category category = categoryRepository.findByCategoryEnum(CategoryEnum.valueOf(rawCategory.toUpperCase()));

        user.addCategory(category);
    }

    @Transactional
    public void delete(Long id) throws IllegalArgumentException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾는 회원이 없습니다."));
        userRepository.deleteById(id);
    }
}
