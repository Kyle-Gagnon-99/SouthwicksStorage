package com.southwicksstorage.southwicksstorage.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value = "auth/login", method = RequestMethod.GET)
	public ModelAndView login(Model model, String error, String logout) {
		
        /*if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");*/
        
		return new ModelAndView("auth/login.html");
	}
	
}
