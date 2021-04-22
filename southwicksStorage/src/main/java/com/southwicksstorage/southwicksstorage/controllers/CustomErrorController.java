package com.southwicksstorage.southwicksstorage.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController{

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView errorPage(Model model, HttpServletRequest request) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String page = "error/error.html";
		
		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				page = "error/404.html";
			}
			
			if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				page = "error/500.html";
			}
			
			if(statusCode == HttpStatus.FORBIDDEN.value()) {
				page = "error/403.html";
			}
		}
		
		return new ModelAndView(page);
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
