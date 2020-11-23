package com.jigi.web.dto;

import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PostRequestDto {
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int personLimit;
    private String rawCategoryEnum;
    private Long studentId; //host정보

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .endDate(endDate)
                .personLimit(personLimit)
                .categoryEnum(CategoryEnum.valueOf(rawCategoryEnum.toUpperCase()))
                .build();
    }


    @Builder
    public PostRequestDto(String title, String content, LocalDate endDate, int personLimit, String rawCategoryEnum, Long studentId) {
        this.title = title;
        this.content = content;
        this.endDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());
        this.personLimit = personLimit;
        this.rawCategoryEnum = rawCategoryEnum;
        this.studentId = studentId;
    }
}
