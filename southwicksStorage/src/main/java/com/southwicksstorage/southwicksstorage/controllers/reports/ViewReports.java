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

import com.southwicksstorage.southwicksstorage.configurations.CommonMethods;
import com.southwicksstorage.southwicksstorage.services.OrderReportService;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.VendorService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewReports {
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private OrderReportService orderReportService;
	
	@Autowired
	private StorageItemService storageItemService;
	
	@RequestMapping(value = "/report/report", method = RequestMethod.GET)
	public ModelAndView getReportByVendor(Model model) {
		
		// Update the order report
		CommonMethods.updateOrderReport(storageItemService.findAll(), orderReportService);
		
		model.addAttribute("vendorList", vendorService.findAll());
		model.addAttribute("orderReportList", orderReportService.findAll());
		return new ModelAndView("reports/reportbyvendor.html");
	}

}
