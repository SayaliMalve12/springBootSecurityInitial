package com.learn.config;

import com.learn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//    }

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeRequests()
//               .antMatchers("/public/**").permitAll()
//               .antMatchers(HttpMethod.GET).permitAll()
               .antMatchers("/public/**").hasRole("ADMIN")
               .antMatchers("/users/**").hasRole("NORMAL")
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
       return http.build();
    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//        return builder.inMemoryAuthentication().withUser("sayali").password("malve").roles("NORMAL")
//                .and().withUser("chetan").password("gaikwad").roles("ADMIN").and().and().build();
//    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("sayali")
              //  .username("user")
                .password(this.passwordEncoder().encode("malve"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername("chetan")
                //  .username("user")
                .password(this.passwordEncoder().encode("gaikwad"))
                .roles("NORMAL")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(10);
    }
}
