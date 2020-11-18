package com.jigi.web.dto.My;

import com.jigi.domain.User.User;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class MyResponseDto {

    private String name;
    private Long studentId;
    private String contact;
    private List<HostedListDto> hosted;
    private List<RegisteredListDto> registered;

    public MyResponseDto(User user){
            this.name =user.getName();
            this.studentId = user.getStudentId();
            this.contact = user.getContact();
            this.hosted= user.getHosted().stream()
                        .map(HostedListDto::new)
                        .collect(Collectors.toList());
            this.registered = user.getRegistered().stream()
                            .map(RegisteredListDto::new)
                            .collect(Collectors.toList());
    }
}
