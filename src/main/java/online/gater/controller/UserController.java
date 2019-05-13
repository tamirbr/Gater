package online.gater.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import online.gater.config.security.LoggedInUser;
import online.gater.lang.ConstantsEn;
import online.gater.model.User;
import online.gater.service.UserService;
import online.gater.utils.Tools;

@Controller
public class UserController {
	@Autowired
    private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	// profile page
    @RequestMapping("/profile")
    public String profilePage(Model model, HttpSession httpSession, HttpServletRequest request) {
        model.addAttribute("con",ConstantsEn.class);
        User user = LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("user",user);
        
        if(!model.containsAttribute("userProfile")) {
            model.addAttribute("userProfile",user);
        }
        
        return "main/profile";
    }
    
    // Register new user
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String editProfile(@Valid User userProfile, BindingResult result, RedirectAttributes redirectAttributes,
    		@RequestParam(value = "userImage",required = false) MultipartFile userImage) {  	
    	// Check for validation errors
    	if(Tools.hasFormPostErrors(result, redirectAttributes)) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfile",result);
            redirectAttributes.addFlashAttribute("userProfile",userProfile);
            return "redirect:/profile";
        }
    	
		User user = LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication());        
		user = userService.findByEmail(user.getEmail());
		user.setEmail(userProfile.getEmail());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		byte[] readyImage = Tools.setImage(userImage);
		if(readyImage != null) {
			user.setImage(Tools.setImage(userImage));    	
		}
		userService.update(user);
		Tools.userChanged(user, redirectAttributes,"updateSuccess",ConstantsEn.PROFILE_UPDATE_SUCCESS);       
        return "redirect:/profile";
    }
    
 // Register new user
    @RequestMapping(value = "/change-pass", method = RequestMethod.POST)
    public String changePass(RedirectAttributes redirectAttributes,
    		@RequestParam(value = "oldPass",required = true) String oldPass,
    		@RequestParam(value = "newPass",required = true) String newPass,
    		@RequestParam(value = "confirmNewPass",required = true) String confirmNewPass) {
		User user = LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication());        
    	validatePassword(redirectAttributes, oldPass, newPass, confirmNewPass, user); 
    	if(redirectAttributes.getFlashAttributes().isEmpty() == false) {
            return "redirect:/profile#change-pass";
    	}
    	
		user = userService.findByEmail(user.getEmail());
		user.setPassword(newPass);
		userService.save(user);
		Tools.userChanged(user, redirectAttributes,"changePassSuccess",ConstantsEn.PASSWORD_UPDATE_SUCCESS);       
        return "redirect:/profile#change-pass";
    }

	private void validatePassword(RedirectAttributes redirectAttributes, String oldPass, String newPass,
			String confirmNewPass, User user) {
		if(passwordEncoder.matches(oldPass, user.getPassword()) == false) {
            redirectAttributes.addFlashAttribute("changePassError",ConstantsEn.PASSWORD_INCORRECT);
    	}else if(confirmNewPass.toString().equals(newPass) == false){
            redirectAttributes.addFlashAttribute("changePassError",ConstantsEn.PASSWORD_CONFIRMATION);
        } else if(confirmNewPass.length() > ConstantsEn.PASSWORD_MAX ||
        		confirmNewPass.length() < ConstantsEn.PASSWORD_MIN) {
            redirectAttributes.addFlashAttribute("changePassError",ConstantsEn.PASSWORD_LENGTH);
        }
	}
}
