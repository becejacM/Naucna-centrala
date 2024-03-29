package ftn.uns.ac.rs.naucnaCentrala.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().permitAll();
                /*.antMatchers("/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/center/logs").hasAuthority("READ_PRIVILEGE")
        		.antMatchers(HttpMethod.GET, "/center/logs/regex").hasAuthority("READ_PRIVILEGE")
        		.antMatchers(HttpMethod.GET,"/center/logs/filter").hasAuthority("READ_PRIVILEGE")
                .antMatchers(HttpMethod.POST,"/center/mac-address").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers(HttpMethod.GET,"/center/mac-address").hasAuthority("READ_PRIVILEGE")
                .antMatchers(HttpMethod.POST, "/center/alarms").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers(HttpMethod.GET, "/center/alarms").hasAuthority("READ_PRIVILEGE")
                .antMatchers(HttpMethod.PUT, "/users/change-password").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .antMatchers(HttpMethod.POST, "/center/rules").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers(HttpMethod.GET, "/center/rules").hasAuthority("READ_PRIVILEGE")
        		.antMatchers(HttpMethod.GET, "/center/rules/all").hasAuthority("READ_PRIVILEGE")
                .antMatchers(HttpMethod.GET, "/center/rules/{name}").hasAuthority("READ_PRIVILEGE");
                .antMatchers(HttpMethod.POST, "/center/logs").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/center/logs").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers(HttpMethod.GET, "/center/logs/regex").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers(HttpMethod.GET, "/center/logs/filter").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers(HttpMethod.GET, "/center/mac-address").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/center/mac-address").hasAuthority("ADMIN");*/
        // Custom JWT based authentication
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
