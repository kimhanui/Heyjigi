package com.jigi.web.dto.My;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jigi.domain.Post.Post;
import com.jigi.web.dto.UserResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HostedListDto {
    private Long id;
    private String title;
    private int personLimit;
    private int personNow;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone="Asia/Seoul")
    private LocalDate endDate;
    private List<UserResponseDto> participants;

    public HostedListDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.personLimit = post.getPersonLimit();
        this.personNow = post.getGuest().size();

        LocalDate localDate = post.getEndDate();
        this.endDate = LocalDate.of(localDate.getYear(),
                localDate.getMonth(), localDate.getDayOfMonth());
        this.participants = post.getGuest().stream()
                            .map(UserResponseDto::new)
                            .collect(Collectors.toList());
    }
}
