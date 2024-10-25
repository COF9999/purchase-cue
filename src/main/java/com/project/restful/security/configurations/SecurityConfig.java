package com.project.restful.security.configurations;


import com.project.restful.enums.Role;
import com.project.restful.security.UserDetailsService;
import jakarta.servlet.FilterRegistration;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationFilter authenticationFilter;


    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              //  .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((request) ->
                {
                    request.requestMatchers("/user/**").permitAll();
                    request.requestMatchers("/admin/**").hasRole(Role.ADMIN.name());
                    request.requestMatchers("/product/**").permitAll();
                    request.requestMatchers("/publication/**").permitAll();
                    request.requestMatchers("/**").permitAll();
                    request.requestMatchers("/images/**").permitAll();
                    request.requestMatchers("/transaction/**").permitAll();
                    request.requestMatchers("/offer/**").permitAll();
                    request.requestMatchers("/transaction/**").permitAll();
                    request.requestMatchers("/denuciations/**").permitAll();
                    request.requestMatchers("/counter-offer/**").permitAll();
                    request.requestMatchers("/commentary/**").permitAll();
                    request.requestMatchers("/comments-publication/**").permitAll();
                    request.requestMatchers("/security/**").permitAll();
                   // request.requestMatchers("/user/**").hasRole(Role.USER.name());
                    request.anyRequest().authenticated();

                })

                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);


    }



    /*
     @Bean
     CorsConfigurationSource corsConfigurationSource(){
         CorsConfiguration config = new CorsConfiguration();
         config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
         config.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
         config.setAllowedOrigins(Arrays.asList("Authorization","Content-Type"));
         config.setAllowCredentials(true);

         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**",config);

         return source;
     }

     @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
         FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
         corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
         return corsBean;
     }

     */
}
