package com.example.demo.controller;


import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.LoginData;
import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.service.PlaylistService;
import com.example.demo.service.SongService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@Controller
public class UsersController{
	
	@Autowired
	UsersService service;
	@Autowired
	SongService songService;
	
@PostMapping("/register")
public String addUsers(@ModelAttribute Users user){
	boolean userStatus=service.emailExists(user.getEmail());
	System.out.println("hiii");
	if(userStatus ==false) {
		service.addUser(user);
		System.out.println("user added");
	}
	else {
		System.out.println("user already exists");
	}
return "login";
}


@PostMapping("/validate")
public String validate(LoginData data,HttpSession session,Model model) {
	System.out.println("call received");
	
	String email=data.getEmail();
	String password=data.getPassword();
	if(service.validateUser(email,password)==true) {
		String role=service.getRole(email);
		session.setAttribute("email", email);
		if(role.equals("admin")) {
			return "adminHome";
		}
		
	else {
		Users user=service.getUsers(email);
		boolean userStatus=user.isPremium();
		List<Song>songList=songService.fetchAllSongs();
		model.addAttribute("songs",songList);
		model.addAttribute("isPremium",userStatus);
		
		return "CustomerHome";
	}
	
}
	else {
		return "Login";
	}

}
@GetMapping("/logout")
public String logout(HttpSession session) {
	session.invalidate();
	return "login";
}

}