package com.jigi.config.oauth;

import com.jigi.domain.User.User;
import com.jigi.domain.User.UserRepository;
import com.jigi.web.dto.OAuthAttributes;
import com.jigi.web.dto.SessionUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 이 클래스에서는 구글 로그인 이후 가져온 사용자의
 * 정보(email,name,picture등) 들을 기반으로
 * 가입 및 정보수정, 세션 저장 등의 기능을 지원합니다.*/
@Transactional
@Log
@AllArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**OAuth2User 확인*/
        log.info("----Custom Service: "+oAuth2User.toString());

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드(여기선 Kakao고정)
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth2 로그인 진행 시 키가 되는 필드값

        /**OAuth2userService를 통해 가져온 OAuth2User의 attribute를 담는 OauthAttributes
         * 즉, resource서버에서 가져온 정보인 것 같다.
         * */
        OAuthAttributes attributes = OAuthAttributes
                .of(userNameAttributeName, oAuth2User.getAttributes()); //registraionId생략.(kakao만 쓰므로)
        User user = saveOrUpdate(attributes);
        log.info("----customOAuth user="+user.toString());
        httpSession.setAttribute("sessionUser", new SessionUser(user)); //세션에 정보 저장
        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRoleKey())
                ),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    /** ROLE 정함 **/
    private User saveOrUpdate(OAuthAttributes attributes){//없으면 insert 있으면 update token
        User user = userRepository.findByOauthId(attributes.getOauthId())
                .map(entity -> entity.update(attributes))
                .orElse(attributes.toEntity()); //toEntity-role정함.
        log.info("---saveOrUpdate="+user.toString());
        return userRepository.save(user);
    }
}