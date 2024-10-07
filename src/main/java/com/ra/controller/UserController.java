package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String login(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }
    @PostMapping("/login")
    public String handlerLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request){
        User userLogin = userService.login(user);
        if(userLogin != null){
            // lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("userLogin",userLogin);
            // chuyển về trang chủ
            return "redirect:/";
        }
        model.addAttribute("err","Sai thông tin tài khoản");
        return "login";
    }

}
