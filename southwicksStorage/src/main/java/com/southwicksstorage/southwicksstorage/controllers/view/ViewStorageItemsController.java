/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateStorageItemFormModel;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStorageItemsController {

	@Autowired
	private StorageItemDao storageItemRepo;
	
	@Autowired
	private VendorDao vendorRepo;
	
	@Autowired
	private TypeOfStorageDao tosRepo;
	
	@RequestMapping(value = "/view/storageItem", method = RequestMethod.GET)
	public ModelAndView getViewStorageItem(Model model) {
		model.addAttribute("storageItemList", storageItemRepo.findAll());
		model.addAttribute("createStorageItemForm", new CreateStorageItemFormModel());
		List<VendorEntity> vendorList = vendorRepo.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = tosRepo.findAll();
		
		if(vendorList.size() > 0) {
			model.addAttribute("vendorList", vendorList);
		}
		
		if(typeOfStorageList.size() > 0) {
			model.addAttribute("typeOfStorageList", typeOfStorageList);
		}
		
		return new ModelAndView("view/viewstorageitem.html");
	}
	
	@RequestMapping(value = "/view/storageItem/findById", method = RequestMethod.GET)
	@ResponseBody
	public StorageItemEntity findStorageItemById(Integer id) {
		return storageItemRepo.findById(id).get();
	}
	
	@RequestMapping(value = "/view/storageItem/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public List<StorageItemEntity> deleteStorageItem(Integer id) {
		return null;
	}
	
	@RequestMapping(value = "/view/storageItem/editItem", method = RequestMethod.POST)
	@ResponseBody
	public StorageItemEntity editStorageItem(Integer id, String name, Integer amount, Integer amountExpected,
			String storedIn, Integer vendor, Integer typeOfStorage, String additionalInfo) {
		
		StorageItemEntity item = storageItemRepo.findById(id).get();
		
		item.setName(name);
		item.setAmount(amount);
		item.setAmountExpected(amountExpected);
		item.setStoredIn(StorageType.valueOf(storedIn));
		item.setVendor(vendorRepo.findById(vendor).get());
		if(tosRepo.findById(typeOfStorage).isPresent()) {
			item.setTypeOfStorage(tosRepo.findById(typeOfStorage).get());
		}
		item.setAdditionalInfo(additionalInfo);
		
		storageItemRepo.save(item);
		
		return item;
	}
	
	@RequestMapping(value = "/view/storageItem/getAllStorageItems", method = RequestMethod.GET)
	@ResponseBody
	public List<StorageItemEntity> getAllItems() {
		return storageItemRepo.findAll();
	}
	
	@RequestMapping(value = "/view/storageItem", method = RequestMethod.POST)
	public ModelAndView postViewStorageItem(@ModelAttribute("createStorageItemForm") CreateStorageItemFormModel itemForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttribute) {
		
		String showModal = "true";
		String modalTitle = "Sorry!";
		String modalType = NotificationTypes.ERROR.getType().toLowerCase();
		String modalMessage = Constants.ERROR_500;
		
		System.out.printf("Name: %s\nAmount: %d\nAmount Expected: %d\nStored In: %s\nVendor: %d\nType Of Storage: %d\nAdditional Information: %s\n",
				itemForm.getName(), itemForm.getAmount(), itemForm.getAmountExpected(), itemForm.getStoredIn(), itemForm.getVendor(), 
				itemForm.getTypeOfStorage(), itemForm.getAdditionalInfo());
		
		if(storageItemRepo.existsByNameAndVendor(itemForm.getName(), vendorRepo.findById(itemForm.getVendor()).get())) {
			List<VendorEntity> vendorList = vendorRepo.findAll();
			List<TypeOfStorageEntity> typeOfStorageList = tosRepo.findAll();
			showModal = "true";
			modalTitle = "Error";
			modalType = NotificationTypes.ERROR.getType().toLowerCase();
			modalMessage = "That item already exists";
			model.addAttribute(Constants.SHOW_MODAL, showModal);
			model.addAttribute(Constants.MODAL_TITLE, modalTitle);
			model.addAttribute(Constants.MODAL_TYPE, modalType);
			model.addAttribute(Constants.MODAL_MESSAGE, modalMessage);
			return new ModelAndView("view/viewstorageitem.html");
		}
		
		VendorEntity vendorToRetreive = null;
		TypeOfStorageEntity tosToRetreive = null;
		
		vendorToRetreive = vendorRepo.findById(itemForm.getVendor()).get();
		Optional<TypeOfStorageEntity> optionalTos = tosRepo.findById(itemForm.getTypeOfStorage());
		
		if(optionalTos.isPresent()) {
			tosToRetreive = optionalTos.get();
		}
		
		StorageItemEntity createItem = new StorageItemEntity(itemForm.getName(), itemForm.getAmount(), itemForm.getAmountExpected(),
				StorageType.valueOf(itemForm.getStoredIn()), itemForm.getAdditionalInfo(), vendorToRetreive, tosToRetreive);
		
		try {
			if(!bindingResult.hasErrors()) {
				storageItemRepo.saveAndFlush(createItem);
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
		
		List<VendorEntity> vendorList = vendorRepo.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = tosRepo.findAll();
		
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
			
			return new ModelAndView("view/viewstorageitem.html");
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
		
		return new ModelAndView("redirect:/view/storageItem");
	}
	
}
