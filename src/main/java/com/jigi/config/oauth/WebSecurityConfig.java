package com.jigi.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final CustomOauth2UserService customOauth2UserService;

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disables cors and csrf
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                .failureUrl("/")
                .defaultSuccessUrl("/")
                .userInfoEndpoint()  /**OAuth2 로그인 성공 후 "사용자 정보를 가져올 때의 설정"들을 담당**/
//                .userService(customOauth2UserService);
;
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new MyOAuth2AuthorizedClientService();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String kakaoClientSecret) {
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder()
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }
}
