package com.jigi.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disables cors and csrf
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/**")
//                "/my/**"
//                        ,"/api/v1/user/**","/api/v1/post"
//                        ,"/logout").hasAnyRole()
//                .antMatchers("/**","/post/find/**","/api/v1/post/**")
                .permitAll()//, "/oauth2/**", "/login/**","/css/**", "/images/**", "/js/**", "/h2-console/**", "/favicon.ico").permitAll()//url관리
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
                .failureUrl("/oauth2/authorization/kakao") //로그인 실패시 -> 다시 로그인 창
                .defaultSuccessUrl("/")
            .and()
                .exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/userdenied"));
                .accessDeniedPage("/userdenied"); //접근 권한 예외 페이지로 이동

        http.logout()
//                .logoutUrl("/api/v1/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String kakaoClientSecret) {
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }
}
