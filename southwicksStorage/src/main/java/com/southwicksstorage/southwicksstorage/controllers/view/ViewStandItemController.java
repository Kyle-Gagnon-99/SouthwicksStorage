/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.configurations.CommonMethods;
import com.southwicksstorage.southwicksstorage.configurations.SystemVariables;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.services.NotificationMessageService;
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.StandItemService;
import com.southwicksstorage.southwicksstorage.services.StandService;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.UserService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStandItemController {

	@Autowired
	private StandService standService;
	
	@Autowired
	private StandItemService standItemService;
	
	@Autowired
	private StorageItemService storageItemService;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private NotificationMessageService notiMessageService;
	
	@Autowired
	private UserService userService;
	
	private Logger log = LoggerFactory.getLogger(ViewStandItemController.class);
	
	@RequestMapping(value = "/view/standItem", method = RequestMethod.GET)
	public ModelAndView getViewStandItem(Model model) {
		
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("standList", standService.findAll());
		model.addAttribute("standItemList", standItemService.findAll());
		model.addAttribute("storageItemList", storageItemService.findAll());
		model.addAttribute("dryStorageItemList", storageItemService.findAllByStorageType(StorageType.DRY_STORAGE));
		model.addAttribute("refridgeItemList", storageItemService.findAllByStorageType(StorageType.REFRIGERATED_STORAGE));
		model.addAttribute("frozenStorageItemList", storageItemService.findAllByStorageType(StorageType.FROZEN_STORAGE));
		model.addAttribute("userRole", auth.getRole());
		return new ModelAndView("view/viewstanditem.html");
	}
	
	@RequestMapping(value = "/view/standItem/updatePage", method = RequestMethod.GET)
	@ResponseBody
	public List<StandItemEntity> getAllStandItems(Model model) {
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("standList", standService.findAll());
		model.addAttribute("standItemList", standItemService.findAll());
		model.addAttribute("storageItemList", storageItemService.findAll());
		model.addAttribute("dryStorageItemList", storageItemService.findAllByStorageType(StorageType.DRY_STORAGE));
		model.addAttribute("refridgeItemList", storageItemService.findAllByStorageType(StorageType.REFRIGERATED_STORAGE));
		model.addAttribute("frozenStorageItemList", storageItemService.findAllByStorageType(StorageType.FROZEN_STORAGE));
		model.addAttribute("userRole", auth.getRole());
		return standItemService.findAll();
	}
	
	@RequestMapping(value = "/view/standItem/getItemsByStand", method = RequestMethod.GET)
	@ResponseBody
	public List<StandItemEntity> getAllItemsByStand(String stand) {
		StandEntity getStand = standService.findByName(stand);
		return standItemService.findAllByStand(getStand);
	}
	
	@RequestMapping(value = "/view/standItem/findById", method = RequestMethod.GET)
	@ResponseBody
	public StandItemEntity findById(Integer id) {
		return standItemService.findById(id);
	}
	
	@RequestMapping(value = "/view/standItem/deleteStandItem", method = RequestMethod.POST)
	@ResponseBody
	public List<StandItemEntity> deleteStandItemById(Integer id) {
		standItemService.delete(standItemService.findById(id));
		return standItemService.findAll();
	}
	
	@RequestMapping(value = "/view/standItem/editStandItem", method = RequestMethod.POST)
	@ResponseBody
	public StandItemEntity editStandItem(Integer id, Integer amount, Integer amountExpected, String additionalInfo) {
		StandItemEntity editStandItem = standItemService.findById(id);
		
		editStandItem.setAmount(amount);
		editStandItem.setAmountExpected(amountExpected);
		editStandItem.setAdditionalInfo(additionalInfo);
		
		float itemPercent = ((float)amount / (float) amountExpected);
		
		if(itemPercent <= SystemVariables.lowStandThreshold) {
			CommonMethods.addAnyNotifications(userService.findAllByRole(Roles.MANAGER), standItemService.findAll(), notiService, notiMessageService);
			CommonMethods.removeAnyNotifications(standService, standItemService, userService, notiService, notiMessageService);
		}
		
		standItemService.save(editStandItem);
		
		return standItemService.findById(id);
	}
	
	@RequestMapping(value = "/view/standItem/createStandItem", method = RequestMethod.POST)
	@ResponseBody
	public StandItemEntity createStandItem(Integer itemId, Integer amount, Integer amountExpected, String additionalInfo, Integer stand) {
		log.info("Called");
		StandEntity getStand = standService.findById(stand);
		StorageItemEntity getItem = storageItemService.findById(itemId);
		
		StandItemEntity createStand = new StandItemEntity(amount, amountExpected, additionalInfo, getItem, getStand);
		
		standItemService.save(createStand);
		
		float itemPercent = ((float)amount / (float) amountExpected);
		
		if(itemPercent <= SystemVariables.lowStandThreshold) {
			CommonMethods.addAnyNotifications(userService.findAllByRole(Roles.MANAGER), standItemService.findAll(), notiService, notiMessageService);
			CommonMethods.removeAnyNotifications(standService, standItemService, userService, notiService, notiMessageService);
		}
		
		return createStand;
	}
	
	@RequestMapping(value = "/view/standItem/getAllItems", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<StandItemEntity> getAllStandItems() {
		return standItemService.findAll();
	}
	
}
