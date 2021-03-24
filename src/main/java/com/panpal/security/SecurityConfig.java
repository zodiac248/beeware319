package com.panpal.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Resource
//    private AccessDeniedHandler accessDeniedHandler;
//    @Resource
//    private JwtFilter jwtFilter;
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().and().csrf().disable()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
//
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//                .authorizeRequests()
//
//                .anyRequest().authenticated();
//
//        httpSecurity.addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/users/login");
//        web.ignoring().antMatchers(HttpMethod.GET, "/users/userinfo");
    }


    private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    public SecurityConfig(OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService) {
        this.oidcUserService = oidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
    }
}