package com.micro.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){

        httpSecurity
                // Configures authorization for requests
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated() // Requires authentication for any request
                )
                .oauth2Login(withDefaults()) // Enables OAuth2 login
                .oauth2Client(withDefaults()) // Configures OAuth2 client support
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()) // Configures JWT token support for the resource server
                );

        return httpSecurity.build();
    }
}
