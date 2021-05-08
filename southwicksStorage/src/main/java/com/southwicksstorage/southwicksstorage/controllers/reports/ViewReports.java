/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.repositories.OrderReportDao;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewReports {
	
	@Autowired
	private VendorDao vendorRepo;
	
	@Autowired
	private OrderReportDao orderReportRepo;
	
	@RequestMapping(value = "/report/report", method = RequestMethod.GET)
	public ModelAndView getReportByVendor(Model model) {
		model.addAttribute("vendorList", vendorRepo.findAll());
		model.addAttribute("orderReportList", orderReportRepo.findAll());
		return new ModelAndView("reports/reportbyvendor.html");
	}

}
