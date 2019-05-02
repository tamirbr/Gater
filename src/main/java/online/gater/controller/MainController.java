package online.gater.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import online.gater.config.security.LoggedInUser;
import online.gater.lang.ConstantsEn;
import online.gater.model.User;
import online.gater.model.UserRole;
import online.gater.service.UserService;
import online.gater.utils.Img;
import online.gater.utils.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MainController {
	@Autowired
    private UserService userService;
	
    // Main page
    @RequestMapping("/")
    public String mainPage(Model model, HttpSession httpSession, HttpServletRequest request) {
        model.addAttribute("con",ConstantsEn.class);
        model.addAttribute("user",LoggedInUser.getUser(SecurityContextHolder.getContext().getAuthentication()));

        return "main/index";
    }

    // login page
    @RequestMapping(value={"/login", "/register"})
    public String loginPage(Model model, HttpSession httpSession, HttpServletRequest request) {
		
    	if(Tools.isNotAuth(SecurityContextHolder.getContext().getAuthentication())) {
    		return "redirect:/";
    	}
		 
        model.addAttribute("con",ConstantsEn.class);
        if(!model.containsAttribute("newUser")) {
            model.addAttribute("newUser",new User());
        }

        return "main/login";
    }
    
    
    // Register new user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid User newUser, BindingResult result, RedirectAttributes redirectAttributes,
    		@RequestParam(value = "userImage",required = false) MultipartFile userImage,
    		@RequestParam(value = "confirmPassword",required = true) String confirmPassword,
    		@RequestParam(value = "terms",required = false) String terms) {
    	
    	validatePasswordAndTermsOfUse(newUser, result, redirectAttributes, confirmPassword, terms);
    	
    	// Check for validation errors
    	if(Tools.hasFormPostErrors(result, redirectAttributes)) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser",result);
            redirectAttributes.addFlashAttribute("newUser",newUser);
            return "redirect:/register#register";
        }
    	
    	registerAction(newUser,userImage, redirectAttributes);

        return "redirect:/register#register";
    }

	private void registerAction(User newUser, MultipartFile userImage, RedirectAttributes redirectAttributes) {
		//TODO: remove user set enabled and auto login to confirm registered email 
		newUser.setNonLocked(true);
		newUser.setEnabled(true);
		newUser.setRole(UserRole.USER);
		newUser.setImage(Tools.setImage(userImage));
    	userService.save(newUser);
        Tools.userChanged(newUser, redirectAttributes, "registerSuccess",ConstantsEn.REGISTER_SUCCESS);
	}

	private void validatePasswordAndTermsOfUse(User newUser, BindingResult result,
			RedirectAttributes redirectAttributes, String confirmPassword, String terms) {
		if(confirmPassword.toString().equals(newUser.getPassword().toString()) == false){
            redirectAttributes.addFlashAttribute("registerError",ConstantsEn.PASSWORD_CONFIRMATION);
        } else if(confirmPassword.length() > ConstantsEn.PASSWORD_MAX ||
        		confirmPassword.length() < ConstantsEn.PASSWORD_MIN) {
            result.addError(new FieldError("newUser", "password", newUser.getPassword(), false,
            		new String[1], null, ConstantsEn.PASSWORD_LENGTH));
        } else if(StringUtils.isEmpty(terms)){
            redirectAttributes.addFlashAttribute("registerError",ConstantsEn.TERMS_AGREE);
        }
	}
	
	// user image
    @RequestMapping("/images/user/{id}.jpg")
    @ResponseBody
    public byte[] userImage(@PathVariable Long id) {
        byte[] image = userService.findById(id).getImage();
        if(image == null || image.toString().isEmpty()) {
            Resource resource = new ClassPathResource("static/materialize/img/user.jpg");
            try {
                InputStream resourceInputStream = resource.getInputStream();
                return IOUtils.toByteArray(resourceInputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else{
            return image;
        }
    }
}
