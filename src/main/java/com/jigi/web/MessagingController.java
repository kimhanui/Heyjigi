package com.jigi.web;

import com.jigi.service.MailService;
import com.jigi.web.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
public class MessagingController {

    private final MailService mailService;

//    @GetMapping("/mail")
//    public String dispMail() {
//        return "mail";
//    }

    @PostMapping("/mail")
    public void execMail(MailDto mailDto) {
        mailService.mailSend(mailDto);
    }
}
