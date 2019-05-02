package online.gater.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import online.gater.config.security.LoggedInUser;
import online.gater.lang.ConstantsEn;
import online.gater.service.UserService;

@Controller
public class AdminController {
	@Autowired
    private UserService userService;
	
	@RequestMapping("/admin-panel")
    public String testPage(Model model, HttpSession httpSession, HttpServletRequest request) {
        model.addAttribute("con",ConstantsEn.class);
        model.addAttribute("user",LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication()));

        return "main/index";
    }
}
