package com.sk.book.springboot.config.auth;

import com.sk.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화 시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable합니다
                .and()
                    .authorizeRequests() //URL별 권한 관리를 설정하는 옵션의 시작점이다, antMatchers옵션 사용하기 위해 반드시 필요
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //권한 관리 대상을 지정하는 옵션이다
                    .anyRequest().authenticated() //설정된 값들 이외 나머지 URL들을 나타냄, 여기선 authenticated()를 추가하여 모두 인증된 사용자들에게만 허용하게 함
                .and()
                    .logout()
                        .logoutSuccessUrl("/") //로그아웃 기능에 대한 여러 설정의 진입점, 성공시 /주소로 이동
                .and()
                    .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당함
                            .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록합니다
    }
}
