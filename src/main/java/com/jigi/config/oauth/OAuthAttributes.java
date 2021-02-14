package com.jigi.config.oauth;

import com.jigi.domain.User.Role.Role;
import com.jigi.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;

    private String registrationId;

    private String nameAttributeKey;

    private String name;

    private String email;

    private String image;
    private String oauthId;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email
            , String registrationId, String image, String oauthId) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.image = image;
        this.oauthId = oauthId;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(registrationId,userNameAttributeName , attributes);
    }

    private static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account"); //email없음
        Map<String, Object> profile = (Map<String, Object>) response.get("profile"); //email없음
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .image((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .oauthId(userNameAttributeName)
                .build();
    }

    public User toEntity() {

        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .profile_image(image)
                .build();
    }
}