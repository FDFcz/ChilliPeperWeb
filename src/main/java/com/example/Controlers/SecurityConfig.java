package com.example.Controlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests // Change to authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .requestMatchers("/static/css/**").permitAll()
                        .requestMatchers("/static/images/**").permitAll()
                        .requestMatchers("/static/js/**").permitAll()
                        .requestMatchers("/", "/WEB-INF/jsp/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/registry/**").permitAll()
                        .requestMatchers("/ViewTables/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf().disable();//allow POST
/*
                //.formLogin(Customizer.withDefaults())
                .formLogin((form) -> form
                        .loginPage("/login") // Custom login page
                        //.loginProcessingUrl("/login") // Form submission URL
                        .defaultSuccessUrl("/ViewTables", false)
                        .permitAll()) // Permit all to access the login page
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(accessDeniedHandler()));
*/

        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }
}


