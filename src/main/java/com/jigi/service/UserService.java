package com.jigi.service;

import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.My.MyResponseDto;
import com.jigi.web.dto.UserRequestDto;
import com.jigi.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void insert(UserRequestDto userRequestDto){
        //중복확인
        Optional<User> user = userRepository.findByStudentId(userRequestDto.getStudentId());
        user.ifPresent(val-> {throw new IllegalStateException("중복되는 회원입니다.");});
        userRepository.save(user.get());
    }

    @Transactional(readOnly = true)
    public UserResponseDto find(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾는 회원이 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll(){
        return userRepository.findAllBystudentIdAsc().stream()
                .map(UserResponseDto::new).collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("찾는 회원이 없습니다."));
        userRepository.deleteById(id);
    }

    // My페이지를 위한 정보조회 메소드
    @Transactional(readOnly = true)
    public MyResponseDto findMyInfo(Long studentId){
        User user = userRepository.findByStudentId(studentId).orElseThrow(()->new IllegalArgumentException("찾는 회원이 없습니다."));
        return new MyResponseDto(user);

    }

}
