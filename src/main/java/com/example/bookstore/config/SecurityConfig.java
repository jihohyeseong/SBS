package com.example.bookstore.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth) -> auth
 //               .requestMatchers("/", "/api/books").permitAll() // 해당 경로에 모두 접근 허가
                  .requestMatchers("/api/admin/books","/api/admin/stock", "/api/admin/total").hasRole("ADMIN") // 해당 경로에 ADMIN이란 롤이 있어야만 접근 허가
//                .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // 마이페이지 my/{id}
  //                .anyRequest().authenticated() // 나머지 요청 처리
                        .anyRequest().permitAll() // **임시로 모든접근 허용중**
        );
        http.formLogin((auth) -> auth
                .loginPage("/login") // 리액트로 해당 주소에 로그인폼 만들기 예(localhost:3000/login)
                .loginProcessingUrl("/loginProc") // 리액트 애플리케이션은 해당 정보를 POST 요청으로 localhost:8080/loginProc(스프링 부트 서버)로 전송,
                // fetch API를 사용하여 /loginProc 엔드포인트로 POST 요청하면 됨
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK); // HTTP 상태 코드를 200으로 설정
                    String username = authentication.getName();
                    log.info(username + " 로그인 성공");
                })
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 실패 시 HTTP 상태 코드를 401로 설정
                    String username = request.getParameter("username"); // 로그인 시도한 사용자 이름 가져오기
                    log.info(username+ " 로그인 실패");
                })
        );
        http.logout((auth) -> auth
                .logoutUrl("/logout") // 여기로 로그아웃 post요청하기
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK); // HTTP 상태 코드를 200으로 설정
                })
                .invalidateHttpSession(true) // HTTP 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
        );
        http.csrf((auth) -> auth.disable());
        http.sessionManagement((auth) -> auth
                .maximumSessions(1) // 아이디당 최대 로그인 개수
                .maxSessionsPreventsLogin(false)); // 중복 접속시 이전 세션 삭제
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId()); // 세션 고정 해커로부터 보호
        return http.build();
    }

    // CORS 해결방법
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://52.79.46.118")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

}
