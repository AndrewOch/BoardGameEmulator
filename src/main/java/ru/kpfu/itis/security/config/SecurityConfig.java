package ru.kpfu.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import ru.kpfu.itis.security.filter.CookieAuthFilter;
import ru.kpfu.itis.security.handler.SuccessfulAuthenticationHandler;

@ComponentScan("ru.kpfu.itis.security")
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customSuccessfulAuthenticationHandler")
    private SuccessfulAuthenticationHandler successfulAuthenticationHandler;

    @Autowired
    @Qualifier("customLogoutHandler")
    private LogoutHandler logoutHandler;

    @Autowired
    @Qualifier("customUserDetailsImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private CookieAuthFilter cookieAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register", "/auth").permitAll()
                .antMatchers("/play", "/games", "/creator", "/").authenticated()
                .and()
                .formLogin()
                .loginPage("/auth")
                .usernameParameter("login")
                .passwordParameter("password")
                .successHandler(successfulAuthenticationHandler)
                .failureUrl("/auth?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .deleteCookies("Auth", "JSESSIONID")
                .logoutSuccessUrl("/auth").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(cookieAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}