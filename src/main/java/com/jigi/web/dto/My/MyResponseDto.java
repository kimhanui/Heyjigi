package com.jigi.web.dto.My;

import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@ToString
@Getter
public class MyResponseDto  {
    private String oauthId;
    private Long studentId;
    private String name;
    private String email;
//    private String accessToken;
    private String profile_image;

    private List<HostedListDto> hosted;
    private List<RegisteredListDto> registered;

    public MyResponseDto(User user){
            this.profile_image = user.getProfile_image();
            this.name =user.getName();
            this.studentId = user.getStudentId();
            this.email = user.getEmail();
            this.oauthId = user.getOauthId();
//            this.accessToken = user.getAccessToken();
            this.hosted= user.getHosted().stream()
                        .map(HostedListDto::new)
                        .collect(Collectors.toList());
            this.registered = user.getRegistered().stream()
                            .map(RegisteredListDto::new)
                            .collect(Collectors.toList());
    }
}
