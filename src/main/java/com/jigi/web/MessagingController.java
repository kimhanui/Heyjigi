package com.jigi.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/v1/api/talk")
public class MessagingController {

    @PostMapping("/message/scrap/send")
    public void messageToFriend(){

    }
}
