package com.myblog.myblog11.config;

import com.myblog.myblog11.security.CustomUserDetailsService;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//role base authority by using another annotation @PreAuthorize on controller method allow for that only
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;//// This bean is responsible for loading user-specific data from the database during authentication

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                //.antMatchers(HttpMethod.POST,"/api/posts").hasRole("ADMIN")//by this only admin can access this url
                .anyRequest()
                .authenticated()
                .and()
                //.formLogin();
               .httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws
            Exception {
        // Configure AuthenticationManagerBuilder to use userDetailsService to retrieve user details from the database
        // and passwordEncoder to encode passwords.
        // userDetailsService: Service responsible for loading user-specific data (username, password, authorities/roles) from the database.
        // passwordEncoder: Encoder used to encode passwords stored in the database.
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }










   //not required bcz we are using database authentication
//   @Bean
//  @Override
//   protected UserDetailsService userDetailsService(){
//       UserDetails user1 = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER").build();
//       UserDetails user2 = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//          return new InMemoryUserDetailsManager(user1,user2);
//    }
              //OR
   //By ashok Sir for in memory authentication for two users
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("ankit").password("1234").authorities("admin").and()
//                .withUser("aditya").password("1234").authorities("user")
//                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

}
