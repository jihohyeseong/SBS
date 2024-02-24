package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	// 로그인 홈페이지 들어가면
    @GetMapping("/login")  
    public String login() {  
        return "login";  
    }
    
    // 로그인 실패하면
    @GetMapping("/loginfailed")  
    public String loginerror(Model model) {  
        model.addAttribute("error", "true");  
        return "login"; 
    } 
    
    // 로그아웃 실행하면
    @GetMapping("/logout")
    public String logout(Model model) {  
        return "login";  
    } 
    
}