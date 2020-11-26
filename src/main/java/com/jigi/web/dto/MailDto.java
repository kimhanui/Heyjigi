package com.jigi.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MailDto {
    private String address; //받는 사람
    private String name;
    private String title;
    private String message;
//    private String categoryEnum;
    @Setter
    private int type; //1: 새 글 2. 내 글

    public MailDto(String address, String name, int type){//, String categoryEnum) {
        this.address = address;
        this.name = name;
//        this.categoryEnum = categoryEnum;
        if (type == 1) {
            this.title = "[Hey Jigi] 관심 카테고리 새 글 알림";
            this.message = "안녕하세요! " + name + "님," + "Hey Jigi입니다.\n 카테고리에 새 글이 올라왔네요! 확인해보세요";
        } else if (type == 2) {
            this.title = "[Hey Jigi] 내 글 새 참여자 알림";
            this.message = "안녕하세요! " + name + "님," + "Hey Jigi입니다.\n 내 글에 새 참여자가 생겼습니다! 확인해보세요";
        }
    }
    public MailDto(UserResponseDto dto, int type){
        this.address = dto.getEmail();
        this.name = dto.getName();
//        this.categoryEnum = categoryEnum;
        if (type == 1) {
            this.title = "[Hey Jigi] 관심 카테고리 새 글 알림";
            this.message = "안녕하세요! " + name + "님," + "Hey Jigi입니다.\n 카테고리에 새 글이 올라왔네요! 확인해보세요";
        } else if (type == 2) {
            this.title = "[Hey Jigi] 내 글 새 참여자 알림";
            this.message = "안녕하세요! " + name + "님," + "Hey Jigi입니다.\n 내 글에 새 참여자가 생겼습니다! 확인해보세요";
        }
    }
}