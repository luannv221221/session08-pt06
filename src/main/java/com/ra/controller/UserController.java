package com.ra.controller;

import com.ra.model.entity.User;
import com.ra.model.service.user.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register";
    }
    @PostMapping("/register")
    public String handlerRegister(@ModelAttribute("user") User user){
        if(categoryService.register(user)){
            return "redirect:/login";
        }
        return "register";
    }
}
