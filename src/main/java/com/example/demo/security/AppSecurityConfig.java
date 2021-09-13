package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,
        proxyTargetClass = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        csrf().disable()
                .
                authorizeRequests()
                //we can have a http method as the first parameter in antMatchers
                .antMatchers("/admin/**").hasRole(ApplicationUserRole.ADMIN.name())
                .anyRequest().authenticated()
        .and()
                .formLogin()
       .loginPage("/login").permitAll()
        .defaultSuccessUrl("/succesfullLogin", true) //true is to force redirecting
        .and()
        .rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
    .key("somethingverysecured") // default is 2 weeks
        //tokenRepository - if we using real database
        .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                //delete above line if we have crsf enabled. (see the documentation)
                .clearAuthentication(true)
                .deleteCookies("remember-me", "JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login");

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin= User.builder()
                .username("admin").password(passwordEncoder.encode("password"))
             //   .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getAuthorities())
                .build();
       UserDetails student=User.builder().username("student").password(passwordEncoder.encode("password"))
             //  .roles(ApplicationUserRole.STUDENT.name())
               .authorities(ApplicationUserRole.STUDENT.getAuthorities())
               .build();
        UserDetails adminTrainee=User.builder().username("adminTrainee").password(passwordEncoder.encode("password")).
        //   roles(ApplicationUserRole.ADMINTRAINEE.name())
                  authorities(ApplicationUserRole.ADMINTRAINEE.getAuthorities())
              .  build();

        return new InMemoryUserDetailsManager(
                admin,
                student,
                adminTrainee
        );
    }

}
