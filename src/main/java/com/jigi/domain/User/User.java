package com.jigi.domain.User;

import com.jigi.domain.BaseEntity;
import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.Post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 10)
    private String name;

    private String contact;
    @Column(nullable = false)
    private Long studentId;

//    @ManyToOne
//    private Role role;

    @OneToMany(mappedBy="host")
    private List<Post> hosted = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="user_post",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="post_id"))
    private List<Post> registered = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum; //enum 결국 열거형


    @Builder
    public User(String name, String contact, Long studentId) {
        this.name = name;
        this.contact = contact;
        this.studentId = studentId;
    }
}
