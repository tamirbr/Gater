package online.gater.config.security;

import online.gater.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    private MyUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl().disable();
        http.authorizeRequests()
                .antMatchers(AllowedPages.PAGES)
                .permitAll()
                
                .antMatchers(AllowedPages.ADMIN).hasRole("ADMIN")
                .antMatchers(AllowedPages.USER).hasAnyRole("ADMIN","USER")
                
                .and()
                .csrf().disable()

                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successForwardUrl("/")

                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "SESSION")
                
                .and()
                .rememberMe().key("uniqueAndSecret")
                .tokenValiditySeconds(604800) // 7 days

                .and()
                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1)
                .expiredUrl("/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
