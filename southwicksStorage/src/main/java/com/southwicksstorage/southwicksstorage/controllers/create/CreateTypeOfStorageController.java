/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.create;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
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
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateTypeOfStorageFormModel;
import com.southwicksstorage.southwicksstorage.services.TypeOfStorageService;

/**
 * @author kyle
 *
 */
@Controller
public class CreateTypeOfStorageController {
	
	@Autowired
	private TypeOfStorageService tosService;

	@RequestMapping(value = "/create/typeOfStorage", method = RequestMethod.GET)
	public ModelAndView getTypeOfStorage(Model model) {
		model.addAttribute("createTypeOfStorageForm", new CreateTypeOfStorageFormModel());
		return new ModelAndView("create/createtypeofstorage.html");
	}
	
	@RequestMapping(value = "/create/typeOfStorage", method = RequestMethod.POST)
	public ModelAndView postTypeOfStorage(@ModelAttribute("createTypeOfStorageForm") CreateTypeOfStorageFormModel tosForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttribute) {
		
		String showModal = "true";
		String modalTitle = "Sorry!";
		String modalType = NotificationTypes.ERROR.getType().toLowerCase();
		String modalMessage = Constants.ERROR_500;
		boolean duplicate = false;
		
		TypeOfStorageEntity storageToSave = new TypeOfStorageEntity(tosForm.getName(), tosForm.getAdditionalInfo());
		boolean findStorage = tosService.existsByName(tosForm.getName());
		
		try {
			if(!StringUtils.isEmpty(tosForm.getName())) {
				if(!findStorage) {
					tosService.save(storageToSave);
				} else {
					bindingResult.rejectValue("name", "error.createTypeOfStorageForm", "Type of storage already exists");
				}
			} else {
				bindingResult.rejectValue("name", "error.createTypeOfStorageForm", "Type of storage can't be empty");
			}
		} catch(Exception e) {
			if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e)
						.getConstraintViolations();

				Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
				ConstraintViolation<?> violation;

				while (iterator.hasNext()) {
					violation = iterator.next();
					bindingResult.rejectValue(violation.getPropertyPath().toString(), "error.createTypeOfStorageForm",
							violation.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "There was an error in creating the type of storage";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			return new ModelAndView("create/createtypeofstorage.html");
		} else {
			showModal = "true";
			modalTitle = "Success!";
			modalType = NotificationTypes.SUCCESS.getType().toLowerCase();
			modalMessage = "Successfully added " + tosForm.getName() + " to type of storage list";;
		}
		
		redirectAttribute.addFlashAttribute(Constants.SHOW_MODAL, showModal);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TITLE, modalTitle);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TYPE, modalType);
		redirectAttribute.addFlashAttribute(Constants.MODAL_MESSAGE, modalMessage);
		return new ModelAndView("redirect:/create/typeOfStorage");
	}
	
}
