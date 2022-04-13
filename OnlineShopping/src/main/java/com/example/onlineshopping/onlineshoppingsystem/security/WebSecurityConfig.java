package com.example.onlineshopping.onlineshoppingsystem.security;

import com.example.onlineshopping.onlineshoppingsystem.security.filter.CustomAuthenticationFilter;
import com.example.onlineshopping.onlineshoppingsystem.security.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //JWT signature secret key
    public static final String SECRET_KEY = "secret-key";
    //token expired time: 10 minutes
    public static final int ACCESS_TOKEN_EXPIRED_TIME = 60 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 1000;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //auth API
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/auth/**").permitAll();
        //product API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/product/get/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        //user API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/get/get-all-users/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/user/manage/**").hasAnyAuthority("ROLE_ADMIN");
        //role API
        http.authorizeRequests().antMatchers("/role/**").hasAnyAuthority("ROLE_ADMIN");

        //authenticated other requests
        http.authorizeRequests().anyRequest().authenticated();
        //permit other requests
//        http.authorizeRequests().anyRequest().permitAll();
    }

}
