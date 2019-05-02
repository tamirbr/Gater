package online.gater.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import online.gater.config.security.LoggedInUser;
import online.gater.lang.ConstantsEn;
import online.gater.model.User;

@Controller
public class PanelController {
	
	// panel page
    @RequestMapping("/panel")
    public String panelPage(Model model, HttpSession httpSession, HttpServletRequest request) {
        model.addAttribute("con",ConstantsEn.class);
        User user = LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("user",user);
        
        
        return "panel/index";
    }
}
