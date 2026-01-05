package com.finedge.finedge.Config;


import com.finedge.finedge.Component.CustomLoginSuccessHandler;
import com.finedge.finedge.Service.Impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Autowired
     private CustomLoginSuccessHandler customLoginSuccessHandler;

      @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http.headers(headers -> headers
                             .cacheControl(cache -> {})
                     )
          . csrf(csrf->csrf.disable())
                     .authorizeHttpRequests(auth->
                             auth.requestMatchers("/",
                                     "/user/signup",
                                     "/user/home",
                                     "/user/userSuccess",
                                     "/user/register",
                                    "/user/login"

                                     ).permitAll()

                                     .anyRequest().authenticated())
                     .formLogin(form->form
                             .loginPage("/login")
                             .loginProcessingUrl("/do-login")
                             .successHandler(customLoginSuccessHandler)
                             .failureUrl("/login?error=true")
                             .permitAll()

                     )
                     .logout(logout->logout
                             .logoutSuccessUrl("/"));



                     return http.build();

      }

      @Bean
       public UserDetailsService userDetailsService()
      {
             return new CustomUserDetailsServiceImpl();
      }

      @Bean
      public PasswordEncoder passwordEncoder(){


          return new BCryptPasswordEncoder();
      }

      @Bean
      public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
             DaoAuthenticationProvider doaAuthenticationProvider = new DaoAuthenticationProvider();

             doaAuthenticationProvider.setUserDetailsService(userDetailsService);
             System.out.println("in Auth");
             doaAuthenticationProvider.setPasswordEncoder(passwordEncoder);

             return new ProviderManager(doaAuthenticationProvider);


      }

}
