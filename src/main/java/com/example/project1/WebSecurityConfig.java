package com.example.project1;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
        public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/users")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }


}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/users").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login") // Specify the login page URL
//                .usernameParameter("email")
//                .defaultSuccessUrl("/users")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout") // Specify the logout URL
//                .logoutSuccessUrl("/")
//                .permitAll();
//    }
//
//    @Bean
//    public WebSecurityConfigurerCustomizer customSecurityConfigurer() {
//        return (webSecurity) -> {
//            // You can customize the WebSecurity here if needed
//            // Example: webSecurity.ignoring().antMatchers("/public/**");
//        };
//    }
//
//    @Bean
//    public UserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsService();
//    }
//}
//
//
//
