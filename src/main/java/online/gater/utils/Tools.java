package online.gater.utils;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import online.gater.lang.ConstantsEn;
import online.gater.model.User;

public class Tools {
	
	public static boolean isNotAuth (Authentication authentication) {
		return (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated());
	}
	
	public static boolean hasFormPostErrors (BindingResult result, RedirectAttributes redirectAttributes) {
		return (result.hasErrors() || redirectAttributes.getFlashAttributes().isEmpty() == false);
	}
	
	public static void userChanged (User user, RedirectAttributes redirectAttributes,String message, String messageData) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        redirectAttributes.addFlashAttribute(message,messageData);  
	}

	public static byte[] setImage (MultipartFile multipartFile) {
		byte[] image = null;
		if(multipartFile != null && multipartFile.isEmpty() == false) {
			try {
				image = Img.cropImage(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		return image;
	}
}
