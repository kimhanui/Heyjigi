package com.jigi.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("#########" + code);
        return "home";
    }
    @GetMapping
    public String getMyAuthenticationFromSession(@AuthenticationPrincipal OAuth2User oAuth2User) { //로그인 후 세션유지
        return oAuth2User.toString();
    }
}
