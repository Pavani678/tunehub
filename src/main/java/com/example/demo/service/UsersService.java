package com.example.demo.service;
import java.util.List;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;

public interface UsersService {
	public String addUser(Users user);
	public boolean emailExists(String email);
	public boolean validateUser(String email,String password);
	public String getRole(String email);
	public Users getUsers(String email);
	public void updateUser(Users user);
	
	
	
}

