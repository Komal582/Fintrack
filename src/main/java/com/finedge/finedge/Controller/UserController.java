package com.finedge.finedge.Controller;


import com.finedge.finedge.Model.User;
import com.finedge.finedge.Service.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

         @Autowired
         private UserService userService;

         @Autowired
         private PasswordEncoder passwordEncoder;



         @GetMapping("/signup")
         public String signupPage(){
             return "userSignup";
         }

         @PostMapping("/register")
         public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password,HttpSession session){
             String hashedPassword = passwordEncoder.encode(password);
             String role="USER";

             User user = new User();
             user.setUsername(name);
             user.setRole(role);
             user.setEmail(email);
             user.setPassword(hashedPassword);

             System.out.println(user);



             userService.saveUser(user);


             return "redirect:/user/userSuccess";

         }




         @GetMapping("/userSuccess")
         public String SuccessPage(){
             return "userSuccess";
         }




         @GetMapping("/update")
         public String update(){

             return "update_user";
         }



         @GetMapping("/dashboard")
         public String userDashboard(){
               return "user_dashboard";
         }



}
