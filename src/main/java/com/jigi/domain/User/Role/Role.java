package com.jigi.domain.User.Role;

import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @JoinColumn
    @ManyToOne
    private User user;
}