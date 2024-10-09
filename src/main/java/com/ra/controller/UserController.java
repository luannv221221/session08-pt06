package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }
    @PostMapping("/register")
    public String handlerRegister(@ModelAttribute("user") User user){
        if(userService.register(user)){
            return "redirect:/login";
        }
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model,
                        @CookieValue(required = false,name = "userCookieEmail") String userCookieEmail,
                        @CookieValue(required = false,name = "userPassword") String userPassword
                        ){
        User user = new User();
        user.setEmail(userCookieEmail);
        user.setPassword(userPassword);
        model.addAttribute("user",user);
        return "login";
    }
    @PostMapping("/login")
    public String handlerLogin(@ModelAttribute("user") User user,
                               Model model,
                               HttpServletRequest request,
                                HttpServletResponse response,
                               @RequestParam(name = "check",required = false) boolean check){
        User userLogin = userService.login(user);


        System.out.println(check);
        if(userLogin != null){
            // lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("userLogin",userLogin);
            if(check){
                // tạo Cookie
                Cookie cookieEmail = new Cookie("userCookieEmail",user.getEmail());
                cookieEmail.setMaxAge(24*60*60);
                Cookie cookiePassword = new Cookie("userPassword",user.getPassword());
                cookiePassword.setMaxAge(24*60*60);
                response.addCookie(cookiePassword);
                response.addCookie(cookieEmail);
            } else {
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    if(cookies[i].getName().equals("userCookieEmail") || cookies[i].getName().equals("userPassword")){
                        cookies[i].setMaxAge(0);
                        response.addCookie(cookies[i]);
                    }
                }
            }
            // chuyển về trang chủ
            return "redirect:/";
        }
        model.addAttribute("err","Sai thông tin tài khoản");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userLogin");
        return "redirect:/";
    }

}
