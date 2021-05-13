package com.little.helpers.security;
import com.little.helpers.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/organisations", "/Users", "/Home", "/Save", "/SaveVolunteers", "/GetVolunteers")
//                .permitAll()
//                .antMatchers("/Authenticate","User").permitAll();

        http.csrf().disable().authorizeRequests()
                 // .antMatchers("/Authenticate").hasRole("USER").and().formLogin();
                .antMatchers("/Authenticate","/Organisations","/Organisations/{title}", "/Users", "/Home", "/Save", "/SaveVolunteer", "/GetVolunteers","/GetVolunteer","/User/{id}","/user/token","/user/logout", "/Products", "/SaveProduct","/DeleteVolunteer","/ChangePassword","/ChangeOrganisationDetails","/DeleteProduct/{title}","/ChangeUserDetails","/UserOrganisations/{emailAddress}","/DeleteOrganisation/{emailAddress}/{title}").permitAll()

                .anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

}

