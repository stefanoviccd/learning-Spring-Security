package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
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
                .antMatchers("/public/*+").permitAll()
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())

                .anyRequest().authenticated()
        .and().httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails lindaSmith= User.builder()
                .username("lindaSmith").password(passwordEncoder.encode("password"))
             //   .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getAuthorities())
                .build();
       UserDetails anaFurtula=User.builder().username("anaFurtula").password(passwordEncoder.encode("password123"))
             //  .roles(ApplicationUserRole.STUDENT.name())
               .authorities(ApplicationUserRole.STUDENT.getAuthorities())
               .build();
        UserDetails tomForks=User.builder().username("tomForks").password(passwordEncoder.encode("password")).
        //   roles(ApplicationUserRole.ADMINTRAINEE.name())
                  authorities(ApplicationUserRole.ADMINTRAINEE.getAuthorities())
              .  build();

        return new InMemoryUserDetailsManager(
                lindaSmith,
                anaFurtula,
                tomForks
        );
    }
}
