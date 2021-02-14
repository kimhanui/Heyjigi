package com.jigi.config.oauth;

import lombok.extern.java.Log;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Log
public enum CustomOAuth2Provider {

    KAKAO {
        @Override
        public ClientRegistration.Builder getBuilder() {
            ClientRegistration.Builder builder = getBuilder("kakao", ClientAuthenticationMethod.POST);
            builder.scope("profile","account_email");
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize");
            builder.tokenUri("https://kauth.kakao.com/oauth/token");
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me");
            builder.userNameAttributeName("id");
            builder.clientName("Kakao");
            builder.clientId("c3fd7ee121c229e092ecaf7a67960298");
            builder.clientSecret("95i43gMteAlEXkkZvjKKyADCvsNvwkd7");
            return builder;
        }
    };

    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(
            String registrationId, ClientAuthenticationMethod method) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUriTemplate(CustomOAuth2Provider.DEFAULT_LOGIN_REDIRECT_URL);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder();
}