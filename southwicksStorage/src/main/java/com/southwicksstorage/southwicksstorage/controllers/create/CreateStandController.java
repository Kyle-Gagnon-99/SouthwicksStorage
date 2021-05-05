/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.create;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateStandFormModel;
import com.southwicksstorage.southwicksstorage.repositories.StandDao;

/**
 * @author kyle
 *
 */
@Controller
public class CreateStandController {
	
	@Autowired
	private StandDao repo;

	@RequestMapping(value = "/create/stand", method = RequestMethod.GET)
	public ModelAndView getCreateStand(Model model) {
		model.addAttribute("createStandForm", new CreateStandFormModel());
		return new ModelAndView("create/createstand.html");
	}
	
	@RequestMapping(value = "/create/stand", method = RequestMethod.POST)
	public ModelAndView postCreateStand(@ModelAttribute("createStandForm") CreateStandFormModel standForm, BindingResult bindingResult,
			RedirectAttributes redirectAttribute, Model model) {
		String showModal = "true";
		String modalTitle = "Sorry!";
		String modalType = NotificationTypes.ERROR.getType().toLowerCase();
		String modalMessage = Constants.ERROR_500;
		
		StandEntity standToCreate = new StandEntity(standForm.getName(), standForm.getAdditionalInfo());
		boolean standExists = repo.existsByName(standForm.getName());
		
		try {
			if(!standExists) {
				repo.saveAndFlush(standToCreate);
			}
		} catch(Exception e) {
			if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e)
						.getConstraintViolations();

				Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
				ConstraintViolation<?> violation;

				while (iterator.hasNext()) {
					violation = iterator.next();
					bindingResult.rejectValue(violation.getPropertyPath().toString(), "error.createStandForm",
							violation.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "There was an error in creating the stand";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			return new ModelAndView("create/createstand.html");
		} else {
			showModal = "true";
			modalTitle = "Success!";
			modalType = NotificationTypes.SUCCESS.getType().toLowerCase();
			modalMessage = "Successfully added " + standForm.getName() + " to the stand list";
		}
		
		redirectAttribute.addFlashAttribute(Constants.SHOW_MODAL, showModal);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TITLE, modalTitle);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TYPE, modalType);
		redirectAttribute.addFlashAttribute(Constants.MODAL_MESSAGE, modalMessage);
		
		return new ModelAndView("redirect:/create/stand");
	}
	
}
