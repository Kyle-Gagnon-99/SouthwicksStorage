/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.create;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateEditVendorModel;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class CreateVendorController {

	@Autowired
	private VendorDao vendorDao;

	@RequestMapping(value = "/create/vendor", method = RequestMethod.GET)
	public ModelAndView getCreateVendor(Model model) {

		model.addAttribute("createEditVendorForm", new CreateEditVendorModel());
		return new ModelAndView("create/createvendor.html");
	}

	@RequestMapping(value = "/create/vendor", method = RequestMethod.POST)
	public ModelAndView postCreateVendor(@ModelAttribute("createEditVendorForm") CreateEditVendorModel vendorFormModel,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttribute) {

		String showModal = "true";
		String modalTitle = "Sorry!";
		String modalType = NotificationTypes.ERROR.getType().toLowerCase();
		String modalMessage = Constants.ERROR_500;
		boolean duplicate = false;

		VendorEntity vendor = new VendorEntity(vendorFormModel.getVendorName(), vendorFormModel.getContactName(),
				vendorFormModel.getContactPhoneNumber(), vendorFormModel.getAdditionalInfo());

		List<VendorEntity> vendorList = vendorDao.findAll();
		List<String> vendorNames = new ArrayList<String>();
		
		if(vendorList.size() > 0) {
			for(int index = 0; index < vendorList.size(); index++) {
				vendorNames.add(vendorList.get(index).getContactName().toLowerCase());
			}
		}
		
		for(int index = 0; index < vendorNames.size(); index++) {
			if(vendorFormModel.getVendorName().toLowerCase().equals(vendorNames.get(index))) {
				duplicate = true;
				break;
			}
		}
		
		try {
			if(!duplicate) {
				vendorDao.saveAndFlush(vendor);
			} else {
				bindingResult.rejectValue("vendorName", "error.createEditVendorForm", "Vendor already exists");
			}
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e)
						.getConstraintViolations();

				Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
				ConstraintViolation<?> violation;

				while (iterator.hasNext()) {
					violation = iterator.next();
					bindingResult.rejectValue(violation.getPropertyPath().toString(), "error.createEditVendorForm",
							violation.getMessage());
				}
			}
		}

		if (bindingResult.hasErrors()) {
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "There was an error in creating the vendor";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			return new ModelAndView("create/createvendor.html");
		} else {
			showModal = "true";
			modalTitle = "Success!";
			modalType = NotificationTypes.SUCCESS.getType().toLowerCase();
			modalMessage = "Successfully added " + vendorFormModel.getVendorName() + " to the vendor list";;
		}
		
		
		redirectAttribute.addFlashAttribute(Constants.SHOW_MODAL, showModal);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TITLE, modalTitle);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TYPE, modalType);
		redirectAttribute.addFlashAttribute(Constants.MODAL_MESSAGE, modalMessage);
		return new ModelAndView("redirect:/create/vendor");
	}

}
