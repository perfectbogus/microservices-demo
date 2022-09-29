package com.microservices.demo.elastic.query.service.config;

import com.microservices.demo.config.UserConfigData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserConfigData userconfigData;
  @Value("${security.paths-to-ignore}")
  private String[] pathsToIgnore;

  public WebSecurityConfig(UserConfigData userconfigData) {
    this.userconfigData = userconfigData;
  }

  @Override
  public void configure(WebSecurity webSecurity) {
    webSecurity
            .ignoring()
            .antMatchers(pathsToIgnore);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
            .and()
            .authorizeRequests().antMatchers("/**").hasRole("USER")
            .and()
            .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser(userconfigData.getUsername())
            .password(passwordEncoder().encode(userconfigData.getPassword()))
            .roles(userconfigData.getRoles());
  }

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
