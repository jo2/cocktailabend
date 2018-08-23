package de.figge.cocktailabend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@PropertySource("classpath:config.properties")
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    //TODO create file config.properties with properties admin.usernam, admin.password and ldap.url and fill it with the credentials
    @Value("${admin.dn}")
    private String adminDn;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${ldap.url}")
    private String ldapUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**", "/console/**", "/statistics/**", "/authenticate").authenticated()
                .antMatchers("/", "/home", "/about", "/all/**", "/first/**", "/ready/**", "/count", "/cocktails", "/gs-guide-websocket/**", "/isReady").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().headers().frameOptions().disable()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns("cn={0},ou=people").groupSearchBase("ou=groups").groupRoleAttribute("member").contextSource()
                .managerDn(adminDn).managerPassword(adminPassword)
                .url(ldapUrl)
                .and().passwordCompare().passwordAttribute("userPassword")
                .and().passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder() {
        final LdapShaPasswordEncoder sha = new LdapShaPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return sha.encodePassword(rawPassword.toString(), null);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return sha.isPasswordValid(encodedPassword, rawPassword.toString(), null);
            }
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }
}