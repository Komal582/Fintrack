package com.finedge.finedge.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {




        authentication.getAuthorities().forEach(authority ->{
            try{
                if(authority.getAuthority().equals("USER")){

                    response.sendRedirect("/user/dashboard");
                } else if (authority.getAuthority().equals("admin")) {

                    response.sendRedirect("/admin/dashboard");
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        } );
    }
}
