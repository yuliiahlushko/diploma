package com.example.excursionPlanning.securityconfig;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final Logger LOG = LoggerFactory.getLogger(SecurityWebConfiguration.class);


    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) {
        try {

            http
                    .csrf()
                    .disable()

                    .authorizeHttpRequests()
                    .requestMatchers(SecurityConstants.SIGN_UP_URLS)
                    .permitAll()

                    .anyRequest()
                    .authenticated()
                    .and()



                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin()
                    .loginPage("/auth/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/web/monuments")


                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/web/logout"))
                    .permitAll();


            return http.build();

        } catch (Exception exception) {
            LOG.error(exception.getMessage());
            return null;
        }
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**");
    }


}
