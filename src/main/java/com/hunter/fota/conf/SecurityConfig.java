package com.hunter.fota.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/upgrade/**").anonymous()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().headers().frameOptions().sameOrigin()
                .and().csrf().disable();
    }
}
