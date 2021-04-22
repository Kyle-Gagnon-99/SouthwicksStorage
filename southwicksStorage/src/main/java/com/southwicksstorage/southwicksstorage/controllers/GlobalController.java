package com.southwicksstorage.southwicksstorage.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.southwicksstorage.southwicksstorage.models.attribute.Modal;

@ControllerAdvice
public class GlobalController {

	@ModelAttribute()
	public void addAttribute(Model model) {
		model.addAttribute("modal", new Modal("false", "", "", ""));
	}
	
}
