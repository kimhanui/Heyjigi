package com.jigi.web.dto;

import com.jigi.domain.User.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long studentId;
    private String name;
    private String contact;
    
    public UserResponseDto(User user){
        this.studentId = user.getStudentId();
        this.name = user.getName();
        this.contact= user.getContact();
    }

}
