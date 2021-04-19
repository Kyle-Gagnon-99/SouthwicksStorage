package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.UserModel;
import com.southwicksstorage.southwicksstorage.models.formModels.EditUserFormModel;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
@Validated
public class ViewUsersController {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDao repo;
	
	@RequestMapping(value = "/view/users", method = RequestMethod.GET)
	public ModelAndView viewUsers(Model model) {
		
		List<UserModelEntity> allUsers = repo.findAll();
		
		model.addAttribute("userList", allUsers);
		model.addAttribute("editUserForm", new EditUserFormModel());
		return new ModelAndView("view/viewusers.html");
	}
	
	@RequestMapping(value = "/view/findById", method = RequestMethod.GET)
	@ResponseBody
	public UserModel returnUser(Integer id) {
		Optional<UserModelEntity> returnedUser =  repo.findById(id);
		UserModelEntity userModel = returnedUser.get();
		UserModel returnedUserModel = new UserModel(userModel.getId(), userModel.getUsername(), userModel.getFirstName(), userModel.getLastName(), userModel.getRole());
		return returnedUserModel;
	}
	
	@RequestMapping(value = "/view/users", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute("editUserForm") EditUserFormModel editUserForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		/* Initialize variables */
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		String showModal = "false";
		String modalType = null;
		String modalTitle = null;
		String modalMessage = null;
		
		/* Find user based on id from form */
		Optional<UserModelEntity> findUser = repo.findById(editUserForm.getId());
		
		if(findUser.isPresent()) {
			UserModelEntity userToEdit = findUser.get();
			
			/* If the username in the form is not equal to the one in the database and the username is the same as another one in the databse
			 * reject it*/
			if(!editUserForm.getUsername().equals(userToEdit.getUsername()) && repo.existsByUsername(editUserForm.getUsername())) {
				bindingResult.rejectValue("username", "error.editUserForm", "Username already exists");
			}
			
			/* Validate before we update the database to catch constraint violations*/
			try {
				UserModelEntity validateInput = new UserModelEntity(editUserForm.getFirstName(), editUserForm.getLastName(), editUserForm.getUsername(),
						Constants.DEFAULT_PASSWORD, editUserForm.getRole());
				Set<ConstraintViolation<UserModelEntity>> violations = validator.validate(validateInput);
				if(!violations.isEmpty()) {
					throw new ConstraintViolationException(violations);
				}
			} catch(Exception e) {
				if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
			        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
			        
			        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
			        ConstraintViolation<?> violation;
			        
			        while(iterator.hasNext()) {
			        	violation = iterator.next();
			        	bindingResult.rejectValue(violation.getPropertyPath().toString(), "error.editUserForm", violation.getMessage());
			        }
			    }
			}
			
			if(bindingResult.hasErrors()) {
				showModal = "true";
				modalType = "error";
				modalTitle = "Failed to Update User";
				modalMessage = "Make sure that the username doesn't already exist, the username isn't empty, or the first name and last name isn't empty.";
			} else {
				userToEdit.setFirstName(editUserForm.getFirstName());
				userToEdit.setLastName(editUserForm.getLastName());
				userToEdit.setRole(editUserForm.getRole());
				userToEdit.setUsername(editUserForm.getUsername());
				if(!StringUtils.isEmpty(editUserForm.getResetPassword())) {
					userToEdit.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
				}
				repo.saveAndFlush(userToEdit);
				showModal  = "true";
				modalType = "success";
				modalTitle = "Success!";
				modalMessage = "Successfully update user " + editUserForm.getUsername();
			}
		} else {
			showModal  = "true";
			modalType = "error";
			modalTitle = "Sorry!";
			modalMessage = "It seems like something went wrong on our end!";
		}
		
		redirectAttributes.addFlashAttribute("showModal", showModal);
		redirectAttributes.addFlashAttribute("modalType", modalType);
		redirectAttributes.addFlashAttribute("modalTitle", modalTitle);
		redirectAttributes.addFlashAttribute("modalMessage", modalMessage);
		return new ModelAndView("redirect:/view/users");
	}
	
}
