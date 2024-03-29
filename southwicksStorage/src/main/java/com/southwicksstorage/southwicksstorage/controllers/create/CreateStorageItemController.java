/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.create;

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
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateStorageItemFormModel;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.TypeOfStorageService;
import com.southwicksstorage.southwicksstorage.services.VendorService;

/**
 * @author kyle
 *
 */
@Controller
public class CreateStorageItemController {

	@Autowired
	private StorageItemService storageItemService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private TypeOfStorageService typeOfStorageService;
	
	@RequestMapping(value = "/create/storageItem", method = RequestMethod.GET)
	public ModelAndView getCreateStorageItem(Model model) {
		
		List<VendorEntity> vendorList = vendorService.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = typeOfStorageService.findAll();
		model.addAttribute("createStorageItemForm", new CreateStorageItemFormModel());
		
		if(vendorList.size() > 0) {
			model.addAttribute("vendorList", vendorList);
		}
		
		if(typeOfStorageList.size() > 0) {
			model.addAttribute("typeOfStorageList", typeOfStorageList);
		}
		
		return new ModelAndView("create/createstorageitem.html");
	}
	
	@RequestMapping(value = "/create/storageItem", method = RequestMethod.POST)
	public ModelAndView postCreateSotrageItem(@ModelAttribute("createStorageItemForm") CreateStorageItemFormModel itemForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttribute) {
		
		String showModal = "true";
		String modalTitle = "Sorry!";
		String modalType = NotificationTypes.ERROR.getType().toLowerCase();
		String modalMessage = Constants.ERROR_500;
		
		System.out.printf("Name: %s\nAmount: %d\nAmount Expected: %d\nStored In: %s\nVendor: %d\nType Of Storage: %d\nAdditional Information: %s\n",
				itemForm.getName(), itemForm.getAmount(), itemForm.getAmountExpected(), itemForm.getStoredType(), itemForm.getVendor(), 
				itemForm.getTypeOfStorage(), itemForm.getAdditionalInfo());
		
		if(storageItemService.existsByNameAndVendor(itemForm.getName(), vendorService.findById(itemForm.getVendor()))) {
			List<VendorEntity> vendorList = vendorService.findAll();
			List<TypeOfStorageEntity> typeOfStorageList = typeOfStorageService.findAll();
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "That item already exists";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			return new ModelAndView("create/createstorageitem.html");
		}
		
		VendorEntity vendorToRetreive = vendorService.findById(itemForm.getVendor());
		TypeOfStorageEntity tosToRetreive = typeOfStorageService.findById(itemForm.getTypeOfStorage());
		
		StorageItemEntity createItem = new StorageItemEntity(itemForm.getName(), itemForm.getAmount(), itemForm.getAmountExpected(),
				StorageType.valueOf(itemForm.getStoredType()), itemForm.getAdditionalInfo(), vendorToRetreive, tosToRetreive);
		
		try {
			if(!bindingResult.hasErrors()) {
				storageItemService.save(createItem);
			}
		} catch(Exception e) {
			System.out.print(e);
			if (e.getCause() instanceof ConstraintViolationException || e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e)
						.getConstraintViolations();

				Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
				ConstraintViolation<?> violation;

				while (iterator.hasNext()) {
					violation = iterator.next();
					bindingResult.rejectValue(violation.getPropertyPath().toString(), "error.createStorageItemForm",
							violation.getMessage());
				}
			}
		}
		
		List<VendorEntity> vendorList = vendorService.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = typeOfStorageService.findAll();
		
		if (bindingResult.hasErrors()) {
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "There was an error in creating the item";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			if(vendorList.size() > 0) {
				model.addAttribute("vendorList", vendorList);
			}
			if(typeOfStorageList.size() > 0) {
				model.addAttribute("typeOfStorageList", typeOfStorageList);
			}
			
			return new ModelAndView("create/createstorageitem.html");
		} else {
			showModal = "true";
			modalTitle = "Success!";
			modalType = NotificationTypes.SUCCESS.getType().toLowerCase();
			modalMessage = "Successfully added " + itemForm.getName() + " to the item list";
		}
		
		redirectAttribute.addFlashAttribute(Constants.SHOW_MODAL, showModal);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TITLE, modalTitle);
		redirectAttribute.addFlashAttribute(Constants.MODAL_TYPE, modalType);
		redirectAttribute.addFlashAttribute(Constants.MODAL_MESSAGE, modalMessage);
		redirectAttribute.addFlashAttribute("vendorList", vendorList);
		redirectAttribute.addFlashAttribute("typeOfStorageList", typeOfStorageList);
		
		return new ModelAndView("redirect:/create/storageItem");
	}
	
}
