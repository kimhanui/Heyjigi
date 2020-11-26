package com.jigi.web.dto;

import com.jigi.domain.User.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String oauthId;
    private Long studentId;
    private String name;
    private String email;

    public UserResponseDto(User user){
        this.oauthId = user.getOauthId();
        this.studentId = user.getStudentId();
        this.name = user.getName();
        this.email= user.getEmail();
//        this.accessToken = user.getAccessToken();
    }

}
