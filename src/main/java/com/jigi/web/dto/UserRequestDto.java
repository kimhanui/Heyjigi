package com.jigi.web.dto;

import com.jigi.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRequestDto {
    private Long studentId;
    private String name;
    private String contact;

    public User toEntity() {
        return User.builder()
                .studentId(studentId)
                .name(name)
                .contact(contact)
                .build();
    }

    @Builder
    public UserRequestDto(Long studentId, String name, String contact) {
        this.studentId = studentId;
        this.name = name;
        this.contact = contact;
    }
}
