package com.github.mclich.ssa.config;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.model.Role;
import com.github.mclich.ssa.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.util.AntPathMatcher;
import javax.sql.DataSource;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final DataSource dataSource;
    private final UserDetailsService userService;

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @SuppressWarnings("unused")
    public GrantedAuthorityDefaults grantedAuthorityDefaults()
    {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    @SuppressWarnings("unused")
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler()
    {
        DefaultWebSecurityExpressionHandler expressionHandler=new DefaultWebSecurityExpressionHandler();
        expressionHandler.setDefaultRolePrefix("");
        return expressionHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity https) throws Exception
    {
        https
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(Constants.ALL_MATCHERS).permitAll()
                .antMatchers(Constants.USER_MATHER).hasRole(Role.USER.getAuthority())
                .antMatchers(Constants.MODERATOR_MATCHER).hasRole(Role.MODERATOR.getAuthority())
                .antMatchers(Constants.ADMIN_MATCHER).hasRole(Role.ADMIN.getAuthority())
                .anyRequest().authenticated()//.anonymous()
            .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, auth)->Constants.popUp(request, response, "login", ((User)auth.getPrincipal()).getFullName(), request.getHeader(HttpHeaders.REFERER)))
                .failureHandler((request, response, exc)->Constants.popUp(request, response, "login", "error", request.getHeader(HttpHeaders.REFERER)))
            .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((request, response, auth)->Constants.popUp(request, response, "logout", "logout", Stream.of(Constants.ALL_MATCHERS).anyMatch(p->new AntPathMatcher().match(request.getRequestURL().toString().replace("/logout", p), request.getHeader(HttpHeaders.REFERER)))?request.getHeader(HttpHeaders.REFERER):"/"))
            .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, exc)->response.sendRedirect(request.getContextPath()+"/403"))
                .authenticationEntryPoint((request, response, exc)->response.sendRedirect(request.getContextPath()+"/403"))
            .and()
                .sessionManagement(sm->sm.invalidSessionUrl("/").maximumSessions(1))
                .headers(headers->headers.referrerPolicy(ReferrerPolicy.SAME_ORIGIN));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
                .userDetailsService(this.userService)
                .passwordEncoder(SecurityConfig.passwordEncoder())
            .and()
                .jdbcAuthentication()
                .dataSource(this.dataSource)
                .usersByUsernameQuery(Constants.USERS_QUERY)
                .authoritiesByUsernameQuery(Constants.ROLES_QUERY);
    }
}