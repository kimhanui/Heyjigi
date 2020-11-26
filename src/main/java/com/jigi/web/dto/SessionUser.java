package com.jigi.web.dto;

import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.io.Serializable;

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

    public SessionUser(User user) {
        log.info("----Session user:"+user.toString());
        this.oauthId = user.getOauthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profile_image = user.getProfile_image();
        log.info("----Session this:"+this.toString());
    }
}