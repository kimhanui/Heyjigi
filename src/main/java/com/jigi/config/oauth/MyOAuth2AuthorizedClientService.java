package com.jigi.config.oauth;

import com.jigi.domain.User.Role.Role;
import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.SessionUser;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * 직접 생성한 구현 클래스 - 인증 정보를 DB에 저장
 **/
@Transactional
@Log
@NoArgsConstructor
@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession httpSession;

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        //정보 추출
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = String.valueOf(oauth2User.getAttributes().get("id"));
        String name = (String) ((LinkedHashMap) ((LinkedHashMap) oauth2User.getAttributes().get("kakao_account")).get("profile")).get("nickname");
        String profile_image = (String) ((LinkedHashMap) oauth2User.getAttributes().get("properties")).get("profile_image");
        String email = (String) ((LinkedHashMap) oauth2User.getAttributes().get("kakao_account")).get("email");
        if (email == null) {
            email = "000000";
        }
        if (profile_image == null) {
            profile_image = "/images/hapshida.jpg";
        }
        log.info("-----oauth getPrincipal: " + authentication.getPrincipal());
        log.info("-----oauth getAttributes: " + oauth2User.getAttributes());

        User user = User.builder()
                .oauthId(id)
                .name(name) //닉네임(실명이기도함)
                .role(Role.USER) /** ROLE 정함 **/
                .accessToken(accessToken.getTokenValue())
                .studentId(000000L)
                .email(email)   //contact도 연결해야됨..
                .profile_image(profile_image)
                .build();
        saveOrUpdate(user);
        httpSession.setAttribute("sessionUser", new SessionUser(user));
    }

    private void saveOrUpdate(User user) {//없으면 insert 있으면 update token
        Optional<User> target = userRepository.findByOauthId(user.getOauthId());
        if (!target.isPresent()) {
            userRepository.save(user);
        } else {
            target.get().update(user);
        }
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }
}