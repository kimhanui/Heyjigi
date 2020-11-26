package com.jigi.web.dto;

import com.jigi.config.oauth.CustomOAuth2Provider;
import com.jigi.domain.User.Role.Role;
import com.jigi.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private String oauthId;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profile_image;
    private Long studentId;

    @Builder
    public OAuthAttributes(String oauthId, Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String profile_image, Long studentId) {
        this.oauthId = oauthId;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.profile_image = profile_image;
        this.studentId = studentId;
    }

    /**
     * OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환.
     * 인증수단이 Kakao 하나이므로'ofKakao'는 생략
     * */
    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        String id = String.valueOf(attributes.get("id"));
        String name = (String) ((LinkedHashMap) ((LinkedHashMap) attributes.get("kakao_account")).get("profile")).get("nickname");
        String profile_image = (String) ((LinkedHashMap)attributes.get("properties")).get("profile_image");
        String email = (String) ((LinkedHashMap)attributes.get("kakao_account")).get("email");
        if(email == null){
            email = "000000";
        }
        if(profile_image == null){
            profile_image = "/images/hapshida.jpg";
        }
        return OAuthAttributes.builder()
                .oauthId(id)
                .name(name)
                .email(email)
                .studentId(0L)
                .profile_image(profile_image) //이름바꿈
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

//    private static OAuthAttributes ofGoogle(String userNameAttributeName,
//                                            Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }


    /**
     * OAuthAttribute에서 엔티티를 생성하는 시점은 처음 가입할 때임.
     * 가입할 때의 기본 권한을 USER로 주기 위해서 role 빌더값에는 Role.USER로 사용
     * */
    public User toEntity() {
        return User.builder()
                .oauthId(oauthId)
                .name(name)
                .email(email)
                .studentId(studentId)
                .profile_image(profile_image)
                .role(Role.USER) //GUEST-> 일반 사용자
                .build();
    }
}