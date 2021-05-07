/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.repositories.StandDao;
import com.southwicksstorage.southwicksstorage.repositories.StandItemDao;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStandItemController {

	@Autowired
	private StandDao standDao;
	
	@Autowired
	private StandItemDao standItemDao;
	
	@Autowired
	private StorageItemDao storageItemDao;
	
	@RequestMapping(value = "/view/standItem", method = RequestMethod.GET)
	public ModelAndView getViewStandItem(Model model) {
		
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("standList", standDao.findAll());
		model.addAttribute("standItemList", standItemDao.findAll());
		model.addAttribute("storageItemList", storageItemDao.findAll());
		model.addAttribute("dryStorageItemList", storageItemDao.findAllByStoredType(StorageType.DRY_STORAGE));
		model.addAttribute("refridgeItemList", storageItemDao.findAllByStoredType(StorageType.REFRIGERATED_STORAGE));
		model.addAttribute("frozenStorageItemList", storageItemDao.findAllByStoredType(StorageType.FROZEN_STORAGE));
		model.addAttribute("userRole", auth.getRole());
		return new ModelAndView("view/viewstanditem.html");
	}
	
	@RequestMapping(value = "/view/standItem/updatePage", method = RequestMethod.GET)
	@ResponseBody
	public List<StandItemEntity> getAllStandItems(Model model) {
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("standList", standDao.findAll());
		model.addAttribute("standItemList", standItemDao.findAll());
		model.addAttribute("storageItemList", storageItemDao.findAll());
		model.addAttribute("dryStorageItemList", storageItemDao.findAllByStoredType(StorageType.DRY_STORAGE));
		model.addAttribute("refridgeItemList", storageItemDao.findAllByStoredType(StorageType.REFRIGERATED_STORAGE));
		model.addAttribute("frozenStorageItemList", storageItemDao.findAllByStoredType(StorageType.FROZEN_STORAGE));
		model.addAttribute("userRole", auth.getRole());
		return standItemDao.findAll();
	}
	
	@RequestMapping(value = "/view/standItem/getItemsByStand", method = RequestMethod.GET)
	@ResponseBody
	public List<StandItemEntity> getAllItemsByStand(String stand) {
		StandEntity getStand = standDao.findByName(stand).get();
		return standItemDao.findAllByStand(getStand);
	}
	
	@RequestMapping(value = "/view/standItem/findById", method = RequestMethod.GET)
	@ResponseBody
	public StandItemEntity findById(Integer id) {
		return standItemDao.findById(id).get();
	}
	
	@RequestMapping(value = "/view/standItem/deleteStandItem", method = RequestMethod.POST)
	@ResponseBody
	public List<StandItemEntity> deleteStandItemById(Integer id) {
		standItemDao.delete(standItemDao.findById(id).get());
		return standItemDao.findAll();
	}
	
	@RequestMapping(value = "/view/standItem/editStandItem", method = RequestMethod.POST)
	@ResponseBody
	public StandItemEntity editStandItem(Integer id, Integer amount, Integer amountExpected, String additionalInfo) {
		StandItemEntity editStandItem = standItemDao.findById(id).get();
		
		editStandItem.setAmount(amount);
		editStandItem.setAmountExpected(amountExpected);
		editStandItem.setAdditionalInfo(additionalInfo);
		
		standItemDao.save(editStandItem);
		
		return standItemDao.findById(id).get();
	}
	
	@RequestMapping(value = "/view/standItem/createStandItem", method = RequestMethod.POST)
	@ResponseBody
	public StandItemEntity createStandItem(Integer itemId, Integer amount, Integer amountExpected, String additionalInfo, Integer stand) {
		
		StandEntity getStand = standDao.findById(stand).get();
		StorageItemEntity getItem = storageItemDao.findById(itemId).get();
		
		StandItemEntity createStand = new StandItemEntity(amount, amountExpected, additionalInfo, getItem, getStand);
		
		standItemDao.save(createStand);
		
		return createStand;
	}
	
	@RequestMapping(value = "/view/standItem/getAllItems", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<StandItemEntity> getAllStandItems() {
		return standItemDao.findAll();
	}
	
}
