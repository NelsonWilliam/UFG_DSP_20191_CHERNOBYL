package br.com.nelsonwilliam.dsp20191.chernobyl.business.security;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configurações da segurança web.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // Authorization
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/admin/**").hasRole(PapelEnum.ADMIN.getRole())
                .antMatchers("/usuario/logout").authenticated()
                .anyRequest().permitAll()
                // Login
                .and()
                .formLogin()
                .loginPage("/usuario/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                // Logout
                .and()
                .logout()
                .logoutUrl("/usuario/logout")
                .deleteCookies("JSESSIONID")
                // Remember-me
                .and()
                .rememberMe();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}