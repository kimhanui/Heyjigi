package com.jigi.web.dto;

import com.jigi.domain.Category.Category;
import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;

@Log
@Getter
@ToString
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String oauthId;
    private Long studentId;
    private String name;
    private String email;
    private String profile_image;
    private List<String> categories;

    public SessionUser(User user) {
        this.oauthId = user.getOauthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
        this.profile_image = user.getProfile_image();
        List<Category> cats = user.getCategories();
        if(cats!=null){
        for(Category category: cats){
            this.categories.add(category.getCategoryEnum().name().toLowerCase());
        }}
    }
}