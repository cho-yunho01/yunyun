package com.delivery.yunyun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize ->
                       authorize
                              .requestMatchers("/api/customer/login").permitAll()
                               .requestMatchers("/api/owner/login").permitAll()
                               .requestMatchers("/api/create").permitAll()
                               .requestMatchers("/api/customer/create").permitAll()
                               .requestMatchers("/api/owner/create").permitAll()
                               .requestMatchers("/api/owner/home").permitAll()
                               .requestMatchers("/api/customer/home").permitAll()

                               // 프론트로 막아두긴 했지만 나중에 고치기
//                               .requestMatchers("/api/customer/home").permitAll()
//                               .requestMatchers("/api/owner/home").permitAll()
                               .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling( exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler()));
        return httpSecurity.build();
    }

}
