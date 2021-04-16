package com.southwicksstorage.southwicksstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(Model model) {
		model.addAttribute("name", "Kyle Gagnon");
		model.addAttribute("role", "Team Member");
		return new ModelAndView("index.html");
	}

}
