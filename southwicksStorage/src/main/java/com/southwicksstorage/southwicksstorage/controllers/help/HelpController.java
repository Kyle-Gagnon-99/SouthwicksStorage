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
		helpContentList.add(new HelpContent("Getting Started",
				"First thing to know is what is the goal of this? The goal of this site is to make managing inventory easier amongst everyone and giving " +
				"the most recent reports to help with ordering with greater accurcy for the week. So this site keeps track of items that are located in storage " +
				"and items in each of the stands. So let's start creating our first item. In order to make our first item we need to create a vendor first. " +
				"We need a vendor because a vendor is required to be used for sorting, searching, and showing up on the reports. After a vendor is created " +
				" it is <ins><strong>suggested</strong></ins> that you create a type of storage. A type of storage describes how the item is held in. For " +
				"example fries are in cases while tubs of ice cream are held in tubs. After you create a vendor and/or a type of storage you need to " + 
				"create a stand. The stand is required to be able to tell the site what items are in what stand. After that we are on our way to create " +
				"storage items and stand items! Here are the steps with links to help pages to go through below: </p>" +
				"<p><ol>" +
				"		<li>Create a <a href=\"/help/manager\">vendor</a></li>" +
				"		<li>(Optional) Create a <a href=\"/help/manager\">type of storage</a></li>" +
				"		<li>Create a <a href=\"/help/manager\">stand</a></li>" +
				"		<li>Create a <a href=\"/help/manager\">storage item</a></li>" +
				"		<li>Create a <a href=\"/help/manager\">stand item</a></li>" +
				"</ol></p>" +
				"<p>After you are setup you will be able to view/edit your items in their respective storage (storage or stand). So let's get started with a " +
				"more in depth guide on how to create an item."));
		helpContentList.add(new HelpContent("Creating A Vendor", 
				"<p>Vendors are one of the most important things to help organize all items and get most things to work. Without adding a vendor you won't be " +
				"able to create any items or view any reports. To create a vendor you need to visit the <a href=\"/create/vendor\">create a vendor</a> page." +
				"<p>To create a vendor you need the vendor name (required), the vendor's contact person's name (optional), the contact's or vendor's phone " +
				"number, and any additional information that you wish to add like account numbers. For more information you can view the help page " +
				"<a href=\"/help/manager\">here</a>"));
		helpContentList.add(new HelpContent("(Optional) Creating A Type of Storage", 
				"<p>When the term Type of Storage is used it is used to refer what is the storage item stored in? For example bags of fries are stored in " +
				"cases and ice cream tubs are stored in tubs. To create a type of storage visit <a href=\"/create/typeOfStorage\">create type of storage</a> page. " +
				"<p>To create a type of storage you will need the name of the type of storage and any additional information you wish to share. Easy as that! " +
				"After you create this type you can use it when creating storage items or editing existing ones. " +
				"For more infiromation you can view the help page <a href=\"/help/manager\">here</a>"));
		helpContentList.add(new HelpContent("Creating A Stand", 
				"<p>Stands are one of the other most important things to help organize all items and get most things to work as well. Without adding a stand " +
				"you won't be able to add items to stands and get any useful reports. To create a stand you need to visit the " +
				"<a href=\"/create/stand\">create a stand</a> page." +
				"<p>To create a stand you need to add the name of a stand and any additional information you would like. For more information you can view " +
				"the help page <a href=\"/help/manager\">here</a>"));
		helpContentList.add(new HelpContent("Creating A Storage Item", 
				"<p>Creating/viewing storage items is the real power of the website. Creating storage items will help you create stand items which use the " +
				"storage items. Creating storage items are a little more complicated then what was described above so it is highly recommended that you " +
				"visit the help page directly <a href=\"/help/create/storageItem\">here</a>"));
		helpContentList.add(new HelpContent("Creating A Stand Item", ""));
		
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
