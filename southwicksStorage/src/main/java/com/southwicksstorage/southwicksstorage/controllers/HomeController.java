package com.southwicksstorage.southwicksstorage.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.services.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(Model model) {
		
		model.addAttribute("existsByUsername", userService.existsByUsername("kgagnon"));
		
		return new ModelAndView("index.html");
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	@ResponseBody
	public List<UserModelEntity> getAllUsers() {
		return userService.findAll();
	}

}
