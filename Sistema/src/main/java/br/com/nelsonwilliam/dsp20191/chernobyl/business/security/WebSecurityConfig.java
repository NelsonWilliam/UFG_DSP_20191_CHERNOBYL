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
                .antMatchers("/testeAcesso/admin/**").hasRole(PapelEnum.ADMIN.getRole())
                .antMatchers("/testeAcesso/usuario/**").hasRole(PapelEnum.USUARIO.getRole())
                .antMatchers("/testeAcesso/logout").authenticated()
                .anyRequest().permitAll()
                // Login
                .and()
                .formLogin()
                .loginPage("/testeAcesso/login")
                .defaultSuccessUrl("/testeAcesso/", true)
                // Logout
                .and()
                .logout()
                .logoutUrl("/testeAcesso/logout")
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