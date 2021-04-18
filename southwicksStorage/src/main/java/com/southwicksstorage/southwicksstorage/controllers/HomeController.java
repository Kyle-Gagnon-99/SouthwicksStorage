package com.southwicksstorage.southwicksstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(Model model, Authentication auth) {
		//CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		//model.addAttribute("name",  user.getFirstName() + " " + user.getLastName());
		//model.addAttribute("role", user.getRole());
		return new ModelAndView("index.html");
	}

}
