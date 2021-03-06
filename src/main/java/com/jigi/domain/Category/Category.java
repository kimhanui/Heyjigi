package com.jigi.domain.Category;


import com.jigi.domain.BaseEntity;
import com.jigi.domain.Post.Post;
import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @OneToMany(mappedBy="category")
    private List<Post> posts;


    @ManyToMany(mappedBy="categories")
    private List<User> users;
}
