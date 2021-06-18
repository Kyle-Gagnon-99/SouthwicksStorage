/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.models.attribute.HelpContent;
import com.southwicksstorage.southwicksstorage.models.attribute.HelpTable;
import com.southwicksstorage.southwicksstorage.models.attribute.HelpTableObject;

/**
 * @author kyle
 *
 */
@Controller
public class HelpController {

	/*
	 * Quickstart Guide for Team Members
	 */
	@RequestMapping(value = "/help/teamMember", method = RequestMethod.GET)
	public ModelAndView getQuickStartGuideTeamMembers(Model model) {
		
		List<HelpContent> helpContentList = new ArrayList<HelpContent>();
		
		helpContentList.add(new HelpContent("Team Member Quickstart Guide Overview",
				"<p>Welcome to the quickstart guide for team members! This quickstart guide will help you with everything you need to know to manage stock here " +
				"at Southwick's Zoo! You should find everthing you need here in this quickstart guide"));
		
		model.addAttribute("helpContentList", helpContentList);
		model.addAttribute("helpPageTitle", "Team Member Quickstart Guide");
		model.addAttribute("helpPageOfLink", "");
		model.addAttribute("helpPageOfTitle", "Home");
		return new ModelAndView("template/help.html");
	}
	
	/*
	 * Quickstart Guide for Managers
	 */
	@RequestMapping(value = "/help/manager", method = RequestMethod.GET)
	public ModelAndView getQuickStartGuideManagers(Model model) {
		
		List<HelpContent> helpContentList = new ArrayList<HelpContent>();
		
		helpContentList.add(new HelpContent("Manager Quickstart Guide Overview",
				"<p>Welcome to the quickstart guide for managers! This quickstart guide will help you with everything you need to know to manage stock here " +
				"at Southwick's Zoo! You should find everthing you need here in this quickstart guide"));
		
		model.addAttribute("helpContentList", helpContentList);
		model.addAttribute("helpPageTitle", "Manager Quickstart Guide");
		model.addAttribute("helpPageOfLink", "");
		model.addAttribute("helpPageOfTitle", "Home");
		return new ModelAndView("template/help.html");
	}
	
	/*
	 * View Audit Log page
	 */
	@RequestMapping(value = "/view/auditLog/help", method = RequestMethod.GET)
	public ModelAndView getViewAuditLogHelpPage(Model model) {
		
		List<HelpContent> helpContentList = new ArrayList<HelpContent>();
		HelpTableObject[] annotationArray = {new HelpTableObject("1", "Search", "The search bar allows the user to be able to search by the date, time, user, item, or anything else in the audit log entries"), 
				new HelpTableObject("2", "Sort", "These arrows allow the table to be filtered. Using the up arrow will sort by ascending and down will be descending"),
				new HelpTableObject("3", "Audit Log Entry", "This is the audit log entry which holds the actual information of the table")};
		HelpTable helpTable = new HelpTable(Arrays.asList(annotationArray));
		
		helpContentList.add(new HelpContent("Audit Log Overview",
				"<p>The Audit Log gives an idea of what user has done to what item. This can be useful to determine who (the user) has either modified, deleted, " +
				"or added an item. The audit log will tell you when the action (modify, delete, or add) was done by who on what item. The audit log will also tell " +
				"you if the item was in storage or in the stand (it will tell you which stand). The audit log is deleted when the audit log entry is 7 days old</p> " +
				"<p><img src=\"/img/help/viewAuditLogHelp.png\" /></p>" +
				helpTable.toString()));
		helpContentList.add(new HelpContent("Audit Log Search / Sort", 
				"<p>(1) Search Bar: The search bar allows for searching for date/time, username, item, and location. For example you can find all Audit Log Entries " +
				"that have actions done by a specific username like the system. You can also search by the item or the location or any subset of text</p>" +
				"<p>(2) Sort: The arrows allows the user to sort the table by the date. You can sort from the most recent to the oldest audit log entry"));
		helpContentList.add(new HelpContent("Audit Log Entry",
				"<p>The audit log entry consists of the same information. The entry will say what action was done on what item, by who, and when. All entries " +
				"follow the same pattern" + 
				"<div class=\"text-center\"><p class=\"fw-bolder\">[Month/Day/Year Time] Item located at Location was Action by Username</p></div>" +
				"<p>The location will either say Storage or the name of the stand the item is located at." +
				"<p>There are 3 actions: Modify, Add, and Delete. Modify edits an existing item. This includes the amount in the stand/storage, name, or anything else. " +
				"Add adds the new item to the location and delete deletes the item from the location"));
		model.addAttribute("helpContentList", helpContentList);
		model.addAttribute("helpPageTitle", "Audit Log Help");
		model.addAttribute("helpPageOfLink", "view/auditLog");
		model.addAttribute("helpPageOfTitle", "View Audit Log");
		return new ModelAndView("template/help.html");
	}
	
}
