package com.southwicksstorage.southwicksstorage.controllers.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.Constants;

@Controller
public class LoginController {

	@RequestMapping(value = "auth/login", method = RequestMethod.GET)
	public ModelAndView login(Model model, String error, String logout, HttpServletRequest request,
			@ModelAttribute(Constants.SHOW_MODAL) String showModal,
			@ModelAttribute(Constants.MODAL_TYPE) String modalType,
			@ModelAttribute(Constants.MODAL_TITLE) String modalTitle,
			@ModelAttribute(Constants.MODAL_MESSAGE) String modalMessage) {
		
        if (error != null) {
        	model.addAttribute("errorMsg", "The username / password combination is incorrect.");
        }
            

        if (logout != null) {
        	model.addAttribute("logoutMsg", "You have been logged out successfully.");
        }
            
        
		return new ModelAndView("auth/login.html");
	}
	
}
