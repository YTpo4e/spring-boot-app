package com.ytpe4ko.springbootapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/cities/*").permitAll()
                .antMatchers(HttpMethod.POST, "/cities").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/poi/*").permitAll()
                .antMatchers(HttpMethod.POST, "/poi").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/poi/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/poi/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/poi/*/comments").authenticated()


                .antMatchers(HttpMethod.GET, "/user/*/place/*").authenticated()
                .antMatchers(HttpMethod.POST, "/user/*/place").authenticated()
                .antMatchers(HttpMethod.DELETE, "user/*/place").authenticated()

                .and()
                .csrf().disable()
                .formLogin().disable();



    }
}
