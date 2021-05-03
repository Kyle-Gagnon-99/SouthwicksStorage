/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.models.VendorModel;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewVendorsController {

	@Autowired
	private VendorDao dao;

	@RequestMapping(value = "/view/vendor", method = RequestMethod.GET)
	public ModelAndView getViewVendor(Model model) {
		
		List<VendorEntity> retreivedVendorList = dao.findAll();
		
		retreivedVendorList.stream().forEach((vendor) -> System.out.print(vendor.getVendorName()));
		model.addAttribute("vendorList", retreivedVendorList);
		
		return new ModelAndView("view/viewvendor.html");
	}

	@RequestMapping(value = "/view/vendor/findById", method = RequestMethod.GET)
	@ResponseBody
	public VendorModel findByVendorId(Integer id) {
		Optional<VendorEntity> vendorList = dao.findById(id);
		VendorEntity vendor = vendorList.get();

		return new VendorModel(vendor.getId(), vendor.getVendorName(), vendor.getContactName(),
				vendor.getContactPhoneNumber(), vendor.getAdditionalInfo());
	}

	@RequestMapping(value = "/view/vendor/deleteVendor", method = RequestMethod.POST)
	@ResponseBody
	public List<VendorModel> deleteVendorById(Integer id) {
		VendorEntity retreivedVendor = dao.findById(id).get();
		dao.delete(retreivedVendor);
		return null;
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
}
