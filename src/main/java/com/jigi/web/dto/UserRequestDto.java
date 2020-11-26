package com.jigi.web.dto;

import com.jigi.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class UserRequestDto {
    private String oauthId;
    private String name;
    private Long studentId;
    private String email;

    public User toEntity() {
        return User.builder()
                .studentId(studentId)
                .email(email)
                .name(name)
                .oauthId(oauthId)
                .build();
    }

    @Builder
    public UserRequestDto(Long studentId, String email, String name, String oauthId) {
        this.name = name;
        this.oauthId =oauthId;
        this.studentId = studentId;
        this.email = email;
    }
}
