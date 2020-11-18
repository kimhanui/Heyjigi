package com.jigi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jigi.domain.Post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private String title;
    private String content;
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate endDate;
    private int personLimit;
    private int personNow; //따로 추가

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getHost().getName();
        this.endDate = post.getEndDate();
        this.personLimit = post.getPersonLimit();
        this.personNow = post.getGuest().size(); //개수 반환
    }
}
