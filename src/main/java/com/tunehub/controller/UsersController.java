package com.tunehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.entities.Users;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	@Autowired
	UsersService service;

	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users users) {
		boolean userStatus = service.emailExists(users.getEmail());
		if (userStatus == false) {
			service.getUser(users);
			System.out.println("User added.");
		} else {
			System.out.println("User already exists");
		}
		return "home";
	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {
		if (service.validateUser(email, password) == true) {
			session.setAttribute("email", email);
			String roles = service.getRole(email);
			if (roles.equals("Admin")) {
				return "adminHome";
			} else {
				return "customerHome";
			}
		} else {
			return "login";
		}
	}

	

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

}
