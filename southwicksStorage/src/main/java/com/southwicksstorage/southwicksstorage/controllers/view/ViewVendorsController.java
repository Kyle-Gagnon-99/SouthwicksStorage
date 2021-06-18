/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.services.VendorService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewVendorsController {

	@Autowired
	private VendorService vendorService;
	
	private Logger log = LoggerFactory.getLogger(ViewVendorsController.class);

	@RequestMapping(value = "/view/vendor", method = RequestMethod.GET)
	public ModelAndView getViewVendor(Model model) {
		
		List<VendorEntity> retreivedVendorList = vendorService.findAll();
		model.addAttribute("vendorList", retreivedVendorList);
		
		return new ModelAndView("view/viewvendor.html");
	}

	@RequestMapping(value = "/view/vendor/deleteVendor", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteVendorById(Integer id) {
		boolean returnValue = true;
		VendorEntity retreivedVendor = vendorService.findById(id);
		
		try {
			vendorService.delete(retreivedVendor);
		} catch(Exception e) {
			log.error("Can't delete vendor {} because there are items and reports connected to these items", retreivedVendor.getVendorName());
			returnValue = false;
		}
		
		return returnValue;
	}

	@RequestMapping(value = "/view/vendor/editVendor", method = RequestMethod.POST)
	@ResponseBody
	public VendorEntity editVendorById(Integer id, String vendorName, String contactName,
			String contactPhoneNumber, String additionalInfo) {
		
		VendorEntity vendorToEdit = vendorService.findById(id);
		
		vendorToEdit.setVendorName(vendorName);
		vendorToEdit.setContactName(contactName);
		vendorToEdit.setContactPhoneNumber(contactPhoneNumber);
		vendorToEdit.setAdditionalInfo(additionalInfo);
		
		vendorService.save(vendorToEdit);
		
		return vendorToEdit;
	}
	
	@RequestMapping(value = "/view/vendor/getAllVendors", method = RequestMethod.POST)
	@ResponseBody
	public List<VendorEntity> getAllVendors() {
		return vendorService.findAll();
	}
}
