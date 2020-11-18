package com.jigi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jigi.domain.Post.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private int personLimit;
    private int personNow;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone="Asia/Seoul")
    private LocalDate endDate;

    public PostListResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getHost().getName();
        this.personLimit = post.getPersonLimit();
        this.personNow = post.getGuest().size();

        LocalDate localDate = post.getEndDate();
        this.endDate = LocalDate.of(localDate.getYear(),
                localDate.getMonth(), localDate.getDayOfMonth());
    }
}
