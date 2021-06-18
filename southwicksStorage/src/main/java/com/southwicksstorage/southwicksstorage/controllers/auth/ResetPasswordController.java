package com.southwicksstorage.southwicksstorage.controllers.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.models.attribute.Modal;
import com.southwicksstorage.southwicksstorage.models.formModels.ResetPasswordFormModel;
import com.southwicksstorage.southwicksstorage.services.UserService;
import com.southwicksstorage.southwicksstorage.validation.ValidatePassword;

@Controller
public class ResetPasswordController {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/auth/resetpassword", method = RequestMethod.GET)
	public ModelAndView getResetPassowrd(Model model) {
		model.addAttribute("resetPasswordForm", new ResetPasswordFormModel());
		return new ModelAndView("auth/resetpassword.html");
	}
	
	@RequestMapping(value = "/auth/resetpassword", method = RequestMethod.POST)
	public ModelAndView postResetPassword(@ModelAttribute("resetPasswordForm") ResetPasswordFormModel resetPasswordForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes, Authentication auth) throws FileNotFoundException, IOException {
		
		Modal modal = null;
		
		CustomUserDetails userCred = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(!StringUtils.isEmpty(resetPasswordForm.getCurrentPassword())) {
			if(!bCryptPasswordEncoder.matches(resetPasswordForm.getCurrentPassword(), userCred.getPassword())) {
				bindingResult.rejectValue("currentPassword", "error.resetPasswordForm", "The password you entered does not match your current password");
			}
		} else {
			bindingResult.rejectValue("currentPassword", "error.resetPasswordForm", "The password you entered does not match your current password");
		}
		
		if(StringUtils.isEmpty(resetPasswordForm.getNewPassword())) {
			bindingResult.rejectValue("newPassword", "error.resetPasswordForm", "New password can not be empty");
		} else {
			if(!ValidatePassword.isPasswordValid(resetPasswordForm.getNewPassword())) {
				bindingResult.rejectValue("newPassword", "error.resetPasswordForm", ValidatePassword.getErrorMessage());
			}
		}
		
		if(!resetPasswordForm.getRetypeNewPassword().equals(resetPasswordForm.getNewPassword()) || StringUtils.isEmpty(resetPasswordForm.getRetypeNewPassword())) {
			bindingResult.rejectValue("retypeNewPassword", "error.resetPasswordForm", "Passwords must match");
		}
		
		if(bindingResult.hasErrors()) {
			modal = new Modal("true", NotificationTypes.ERROR.getType().toLowerCase(), "Failed to reset password", "Failed");
			model.addAttribute("modal", modal);
			return new ModelAndView("auth/resetpassword.html");
		}
		
		String newEncodedPassword = bCryptPasswordEncoder.encode(resetPasswordForm.getNewPassword());
		
		UserModelEntity user = userService.findById(userCred.getId());
		if(user != null) {
			user.setPassword(newEncodedPassword);
			userService.save(user);
		} else {
			modal = new Modal("true", NotificationTypes.ERROR.getType().toLowerCase(), "Sorry!", "Sorry about that! It seems like something went wrong on our end");
			redirectAttributes.addFlashAttribute("modal", modal);
			return new ModelAndView("redirect:/auth/resetpassword");
		}
		modal = new Modal("true", NotificationTypes.SUCCESS.getType().toLowerCase(), "Success!", "Successfully reseted password");
		redirectAttributes.addFlashAttribute("modal", modal);
		
		return new ModelAndView("redirect:/auth/login?logout");
	}
	
}
