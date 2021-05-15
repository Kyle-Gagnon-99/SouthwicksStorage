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
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewVendorsController {

	@Autowired
	private VendorDao dao;
	
	private Logger log = LoggerFactory.getLogger(ViewVendorsController.class);

	@RequestMapping(value = "/view/vendor", method = RequestMethod.GET)
	public ModelAndView getViewVendor(Model model) {
		
		List<VendorEntity> retreivedVendorList = dao.findAll();
		model.addAttribute("vendorList", retreivedVendorList);
		
		return new ModelAndView("view/viewvendor.html");
	}

	@RequestMapping(value = "/view/vendor/deleteVendor", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteVendorById(Integer id) {
		boolean returnValue = true;
		VendorEntity retreivedVendor = dao.findById(id).get();
		
		try {
			dao.delete(retreivedVendor);
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
		
		VendorEntity vendorToEdit = dao.findById(id).get();
		
		vendorToEdit.setVendorName(vendorName);
		vendorToEdit.setContactName(contactName);
		vendorToEdit.setContactPhoneNumber(contactPhoneNumber);
		vendorToEdit.setAdditionalInfo(additionalInfo);
		
		dao.save(vendorToEdit);
		
		return vendorToEdit;
	}
	
	@RequestMapping(value = "/view/vendor/getAllVendors", method = RequestMethod.POST)
	@ResponseBody
	public List<VendorEntity> getAllVendors() {
		return dao.findAll();
	}
}
