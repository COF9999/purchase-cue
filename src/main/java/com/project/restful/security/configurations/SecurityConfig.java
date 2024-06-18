package com.project.restful.security.configurations;


import com.project.restful.enums.Role;
import com.project.restful.security.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationFilter authenticationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((request) ->
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
                   // request.requestMatchers("/user/**").hasRole(Role.USER.name());
                    request.anyRequest().authenticated();

                }).userDetailsService(userDetailsService)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter,

                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration)

            throws Exception {
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
}
