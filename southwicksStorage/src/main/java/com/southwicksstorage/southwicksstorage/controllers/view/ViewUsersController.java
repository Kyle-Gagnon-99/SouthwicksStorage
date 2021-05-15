package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.UserModel;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
@Validated
public class ViewUsersController {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDao repo;
	
	private List<UserModel> usersList = null;
	private Logger log = LoggerFactory.getLogger(ViewUsersController.class);
	
	@RequestMapping(value = "/view/users", method = RequestMethod.GET)
	public ModelAndView viewUsers(Model model) {
		return new ModelAndView("view/viewusers.html");
	}
	
	@RequestMapping(value = "/view/users/editUser", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUser(Integer id, String username, String firstName, String lastName, String role, Boolean resetPassword) {
		UserModelEntity getUser = repo.findById(id).get();
		
		getUser.setUsername(username);
		getUser.setFirstName(firstName);
		getUser.setLastName(lastName);
		getUser.setRole(Roles.valueOf(role));
		if(resetPassword) {
			getUser.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
		}
		
		try {
			repo.save(getUser);
		} catch(Exception e) {
			log.error("Can not update user. Stacktrace follows.");
			log.error(e.getMessage());
			return false;
		}
		setUsersList();
		return true;
	}
	
	@RequestMapping(value = "/view/users/userExists", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkIfUserExists(String username) {
		return !repo.existsByUsername(username);
	}
	
	@RequestMapping(value = "/view/users/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteUser(Integer id) {
		try {
			repo.delete(repo.findById(id).get());
		} catch(Exception e) {
			log.error("Can not update user. Stacktrace follows.");
			log.error(e.getMessage());
			return false;
		}
		
		setUsersList();
		return true;
	}
	
	@RequestMapping(value = "/view/users/getAllUsers", method = RequestMethod.POST)
	@ResponseBody
	public List<UserModel> getAllUsers() {
		
		if(usersList == null) {
			setUsersList();
		}
		
		return usersList;
	}
	
	private void setUsersList() {
		List<UserModelEntity> listOfUsers = repo.findAll();
		
		if(usersList != null) {
			usersList.clear();
		} else {
			usersList = new ArrayList<UserModel>();
		}
		
		listOfUsers.stream().forEach((user) -> {
			usersList.add(new UserModel(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
					user.getRole().toString(), user.getRole()));
		});
	}
	
}
