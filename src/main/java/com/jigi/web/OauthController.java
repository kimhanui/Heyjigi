package com.jigi.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

//    @GetMapping
//    public ResponseEntity<Model> getMyAuthenticationFromSession(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) { //로그인 후 세션유지
//        try{
//            model.addAttribute("user",oAuth2User.toString());
//            return new ResponseEntity<>(model, HttpStatus.OK);
//        }catch(NullPointerException e){
//            model.addAttribute("user", "null");
//            return new ResponseEntity<>(model,HttpStatus.OK);
//        }
//    }
}
