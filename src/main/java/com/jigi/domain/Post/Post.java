package com.jigi.domain.Post;

import com.jigi.domain.BaseEntity;
import com.jigi.domain.Category.CategoryEnum;
import com.jigi.domain.User.User;
import com.jigi.web.dto.UserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;
    private LocalDate endDate;
    private int personLimit;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum; // save시: 생성자에 enum('arduino') 넣음 -> this.getEnum()해서 알람줌

    @ManyToOne
    private User host;

    @ManyToMany(mappedBy = "registered")
    private List<User> guest = new ArrayList<>();

    @Builder
    public Post(String title, String content, LocalDate endDate, int personLimit, CategoryEnum categoryEnum, User host){
        this.title=title;
        this.content=content;
        this.endDate = LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth());
        this.personLimit = personLimit;
        this.categoryEnum = categoryEnum;
        this.host = host;
    }

    public void setHost(User host){
        this.host = host;
    }
    public void addGuest(User guest){
        this.guest.add(guest);
        if(guest.getRegistered().contains(this) == false){
            guest.getRegistered().add(this);
        }
    }
    public void deleteGuest(User guest){
        this.guest.remove(guest);
        if(guest.getRegistered().contains(this) == true){
            guest.getRegistered().remove(this);
        }
    }
}
