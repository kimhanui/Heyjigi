package com.jigi.web.dto.My;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jigi.domain.Post.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisteredListDto {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private String author;
    public RegisteredListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getHost().getName();
        LocalDate localDate = post.getEndDate();
        this.endDate = LocalDate.of(localDate.getYear(),
                localDate.getMonth(), localDate.getDayOfMonth());
    }
}
