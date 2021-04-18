package com.southwicksstorage.southwicksstorage.controllers.create;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateEditUserFormModel;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
public class CreateUserController {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDao userRepo;
	
	private static final String DEFAULT_PASSWORD = "Password123!";
	
	@RequestMapping(value = "/create/user", method = RequestMethod.GET)
	public ModelAndView getCreateUser(Model model) {
		model.addAttribute("createEditUserForm", new CreateEditUserFormModel());
		model.addAttribute("name", "Kyle Gagnon");
		model.addAttribute("role", "Team Member");
		return new ModelAndView("create/createuser.html");
	}
	
	@RequestMapping(value = "/create/user", method = RequestMethod.POST)
	public ModelAndView postCreateUser(@ModelAttribute("createEditUserForm") CreateEditUserFormModel createEditUserForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {

		if(userRepo.existsByUsername(createEditUserForm.getUsername())) {
			result.rejectValue("username", "error.createEditUserForm", "Username already exists");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
		
		UserModelEntity addUser = new UserModelEntity(createEditUserForm.getFirstName(), createEditUserForm.getLastName(), createEditUserForm.getUsername(), encodedPassword, createEditUserForm.getRole());
		
		try {
			userRepo.saveAndFlush(addUser);
		} catch(Exception e) {
		   if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
		        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
		        
		        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
		        ConstraintViolation<?> violation;
		        
		        while(iterator.hasNext()) {
		        	violation = iterator.next();
		        	result.rejectValue(violation.getPropertyPath().toString(), "error.createEditUserForm", violation.getMessage());
		        }
		    }
		}
		
		if(result.hasErrors()) {
			ModelAndView mav = new ModelAndView("create/createUser.html");
			mav.addObject("showModal", "true");
			mav.addObject("modalType", "error");
			mav.addObject("modalMessage", "There was an error adding the user");
			mav.addObject("modalTitle", "Error Adding User");
			return mav;
		}
		
		redirectAttributes.addFlashAttribute("showModal", "true");
		redirectAttributes.addFlashAttribute("modalType", "success");
		redirectAttributes.addFlashAttribute("modalTitle", "Adding User Successful");
		redirectAttributes.addFlashAttribute("modalMessage", "User " + createEditUserForm.getUsername() + " was successfully added");
		return new ModelAndView("redirect:/create/user");
	}
	
}
