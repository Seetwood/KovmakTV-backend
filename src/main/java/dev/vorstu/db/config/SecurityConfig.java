package dev.vorstu.db.config;

import javax.sql.DataSource;

import dev.vorstu.db.enums.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, true from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        http
                .httpBasic().and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/api/currentUser").hasAnyAuthority(RoleUser.SUPER_USER.name())
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()

                .antMatchers(HttpMethod.DELETE, "/api/profile/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())

                .antMatchers(HttpMethod.GET,  "/api/profile/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())
                .antMatchers(HttpMethod.POST,  "/api/profile/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())
                .antMatchers(HttpMethod.PUT,  "/api/profile/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())
                .antMatchers(HttpMethod.DELETE,  "/api/profile/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())

                .antMatchers(HttpMethod.GET,  "/api/film/**").permitAll()
                .antMatchers(HttpMethod.POST,  "/api/film/**").hasAnyAuthority(RoleUser.SUPER_USER.name())
                .antMatchers(HttpMethod.PUT,  "/api/film/**").hasAnyAuthority(RoleUser.SUPER_USER.name())
                .antMatchers(HttpMethod.DELETE,  "/api/film/**").hasAnyAuthority(RoleUser.SUPER_USER.name())

                .antMatchers(HttpMethod.GET,  "/api/film/genres/**").permitAll()
                .antMatchers(HttpMethod.POST,  "/api/film/genres/**").hasAnyAuthority(RoleUser.SUPER_USER.name())
                .antMatchers(HttpMethod.PUT,  "/api/film/genres/**").hasAnyAuthority(RoleUser.SUPER_USER.name())
                .antMatchers(HttpMethod.DELETE,  "/api/film/genres/**").hasAnyAuthority(RoleUser.SUPER_USER.name())

                .antMatchers(HttpMethod.GET,  "/api/reviews/**").permitAll()
                .antMatchers(HttpMethod.POST,  "/api/reviews/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())
                .antMatchers(HttpMethod.PUT,  "/api/reviews/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())
                .antMatchers(HttpMethod.DELETE,  "/api/reviews/**").hasAnyAuthority(RoleUser.SUPER_USER.name(), RoleUser.USER.name())

                .antMatchers("/ws/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable();
        http.cors().disable();
    }
}
