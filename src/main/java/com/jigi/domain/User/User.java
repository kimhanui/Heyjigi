package com.jigi.domain.User;

import com.jigi.domain.BaseEntity;
import com.jigi.domain.Category.Category;
import com.jigi.domain.Post.Post;
import com.jigi.domain.User.Role.Role;
import com.jigi.web.dto.OAuthAttributes;
import com.jigi.web.dto.UserRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /********** 기본 정보 **********/
    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column//(nullable = false)
    private Long studentId;

    @Column
    private String profile_image;

    @Enumerated(EnumType.STRING)
    @Column//(nullable = false)
    private Role role;
    /********** oauth 프로퍼티 **********/
    @Column
    private String oauthId; //oauth

    @Column
    private String accessToken;

    /********** 연관관계3 **********/
    @OneToMany(mappedBy="host")
    private List<Post> hosted = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="user_post",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="post_id"))
    private List<Post> registered = new ArrayList<>();

    @JoinTable(name="user_category",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))

    @ManyToMany
    private List<Category> categories;

    @Builder
    public User(String name, String email, Long studentId,String oauthId,// String providerName, String accessToken
                Role role, String profile_image) {
//        this.accessToken = accessToken;
//        this.providerName = providerName;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.role = role;
        this.profile_image = profile_image;
    }
    public void addCategory(Category category){
        if(this.categories.contains(category) == false)
            this.categories.add(category);
        if(category.getUsers().contains(this) == false)
            category.getUsers().add(this);
    }
    public void deleteCategory(Category category){
        if(this.categories.contains(category) == true)
            this.categories.remove(category);
        if(category.getUsers().contains(this) == true)
            category.getUsers().remove(this);
    }
    public User  update(OAuthAttributes oAuthAttributes){
        this.oauthId = oAuthAttributes.getOauthId();
        this.name = oAuthAttributes.getName();
        this.email = oAuthAttributes.getEmail();
        this.profile_image = oAuthAttributes.getProfile_image();
        return this;
    }
    public void updateBasic(UserRequestDto userRequestDto){
          this.name =userRequestDto.getName();
          this.studentId = userRequestDto.getStudentId();
          this.email =userRequestDto.getEmail();
    }
//    public void updateToken(String accessToken){
//        this.accessToken = accessToken;
//    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
