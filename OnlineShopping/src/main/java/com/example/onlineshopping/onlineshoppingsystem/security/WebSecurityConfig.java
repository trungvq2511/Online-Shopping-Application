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
    //token expired time: 1 days
    public static final int ACCESS_TOKEN_EXPIRED_TIME = 24 * 60 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRED_TIME = ACCESS_TOKEN_EXPIRED_TIME * 7;

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
        //user API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/get/get-all-users/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/user/manage/**").hasAnyAuthority("ROLE_ADMIN");
        //role API
        http.authorizeRequests().antMatchers("/role/**").hasAnyAuthority("ROLE_ADMIN");
        //product API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/product/get/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/product/manage/**").hasAnyAuthority("ROLE_ADMIN");
        //category API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/category/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/category/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/category/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/category/**").hasAnyAuthority("ROLE_ADMIN");
        //cart API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cart/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cart/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cart/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cart/**").hasAnyAuthority("ROLE_USER");
        //invoice API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/invoice/get/all-invoices-by-user/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/invoice/get/all-invoices/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/invoice/createInvoice/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/invoice/manage/**").hasAnyAuthority("ROLE_ADMIN");
        //rating API
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/rating/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/rating/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/rating/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/rating/**").authenticated();


        //authenticated other requests
        http.authorizeRequests().anyRequest().authenticated();
        //permit other requests
//        http.authorizeRequests().anyRequest().permitAll();
    }

}
