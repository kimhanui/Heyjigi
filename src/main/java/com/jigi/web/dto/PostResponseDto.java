package com.jigi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jigi.domain.Post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String author_oauthId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate endDate;
    private int personLimit;
    private int personNow; //따로 추가
    private String rawCategoryEnum;
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getHost().getName();
        this.author_oauthId = post.getHost().getOauthId();
        this.endDate = post.getEndDate();
        this.personLimit = post.getPersonLimit();
        this.personNow = post.getGuest().size(); //개수 반환
        this.rawCategoryEnum = post.getCategory().getCategoryEnum().toString().toLowerCase();
    }
}
