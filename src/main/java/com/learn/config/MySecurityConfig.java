package com.learn.config;

import com.learn.models.CustomUserDetail;
import com.learn.services.CusotmUserDetailService;
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
    private CusotmUserDetailService cusotmUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //1. httpBasic
//       http
//               .csrf().disable()
//               .authorizeRequests()
////               .antMatchers("/public/**").permitAll()
////               .antMatchers(HttpMethod.GET).permitAll()
//               .antMatchers("/public/**").hasRole("ADMIN")
//               .antMatchers("/users/**").hasRole("NORMAL")
//               .anyRequest()
//               .authenticated()
//               .and()
//               .httpBasic();

        //2. FormLogin
        http
                .csrf().disable()
                .authorizeRequests()
//               .antMatchers("/public/**").permitAll()
//               .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/public/**").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("NORMAL")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/users/");
       return http.build();
    }

//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
////        return builder.inMemoryAuthentication().withUser("sayali").password("malve").roles("NORMAL")
////                .and().withUser("chetan").password("gaikwad").roles("ADMIN").and().and().build();
//        return builder.userDetailsService(cusotmUserDetailService).passwordEncoder(passwordEncoder()).and().build();
//    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withUsername("sayali")
//                .username("sayali")
//                .password(this.passwordEncoder().encode("malve"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.withUsername("chetan")
//                .username("chetan")
//                .password(this.passwordEncoder().encode("gaikwad"))
//                .roles("NORMAL")
//                .build();
//        return new InMemoryUserDetailsManager(user, user2);
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(10);
    }


}
