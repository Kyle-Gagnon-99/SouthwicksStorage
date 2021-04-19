package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateEditUserFormModel;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
public class ViewUsersController {

	@Autowired
	private UserDao repo;
	
	@RequestMapping(value = "/view/users", method = RequestMethod.GET)
	public ModelAndView viewUsers(Model model) {
		
		List<UserModelEntity> allUsers = repo.findAll();
		
		System.out.print("Last Name: " + allUsers.get(0).getLastName());
		
		model.addAttribute("userList", allUsers);
		model.addAttribute("createEditUserForm", new CreateEditUserFormModel());
		return new ModelAndView("view/viewusers.html");
	}
	
}
