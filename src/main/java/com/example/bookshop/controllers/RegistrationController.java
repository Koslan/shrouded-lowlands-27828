package com.example.bookshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bookshop.models.Role;
import com.example.bookshop.models.User;
import com.example.bookshop.repositories.RoleRepository;
import com.example.bookshop.repositories.UserRepository;

@Controller
public class RegistrationController {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/registration")
	public String getRegistrationPage() {
		return "registration";
	}

	@PostMapping(value = "/registration")
	private String addUser(@ModelAttribute User user) {
		boolean isEmpty = true;

		List<User> users = userRepository.findAll();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getLogin().equals(user.getLogin())) {
				isEmpty = false;
				break;
			}
		}
		if (isEmpty) {
			List<Role> userRole = roleRepository.findAll();
			for (int i = 0; i < userRole.size(); i++) {
				if (!userRole.get(i).getRoles().equals("user")) {
					userRole.remove(i);
				}
			}
			user.setRoles(userRole);
			userRepository.saveAndFlush(user);
			return "redirect:/login";
		}
		return "registration";
	}
}
