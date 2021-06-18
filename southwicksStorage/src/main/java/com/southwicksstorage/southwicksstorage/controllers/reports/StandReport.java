/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.services.StandItemService;
import com.southwicksstorage.southwicksstorage.services.StandService;

/**
 * @author kyle
 *
 */
@Controller
public class StandReport {

	@Autowired
	private StandService standService;
	
	@Autowired
	private StandItemService standItemService;
	
	@RequestMapping(value = "/report/standReport", method = RequestMethod.GET)
	public ModelAndView getStandReport(Model model) {
		
		List<StandItemEntity> standItemList = new ArrayList<StandItemEntity>();
		
		standItemService.findAll().stream().forEach((item) -> {
			if(item.getAmount() < item.getAmountExpected()) {
				standItemList.add(item);
			}
		});
		
		model.addAttribute("standList", standService.findAll());
		model.addAttribute("standItemList", standItemList);
		return new ModelAndView("reports/standreport.html");
	}
	
}
