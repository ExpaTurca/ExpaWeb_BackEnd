/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.DTOs.UserDTO;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.repository.UserRepository;
import com.expastudios.blogweb.services.IServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<UserDTO> getUser(String email) {

		Optional<UserDTO> profileDTO = userRepository
				.findByEmailAndIsActiveTrue(email)
				.map(map -> {
					try {
						return (UserDTO) EntityDtoConversion.ConvertToDTO(map);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				});

		return ResponseEntity.ok(profileDTO.orElseThrow());
	}

	@Override
	public ResponseEntity<?> saveUser(NewUserForm userForm, HttpServletRequest request, HttpServletResponse response) {

		try {
			User user = new User();
			user.setFirstName(userForm.getFirstName());
			user.setLastName(userForm.getLastName());
			user.setEmail(userForm.getEmail());
			user.setUsername(userForm.getUsername());
			user.setPassword(userForm.getPassword());
			user.setRegisteredAt(Zone.getCurrentTime());
			userRepository.save(user);
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok(true);
	}

	@Override
	public ResponseEntity<Boolean> editUser(User user, HttpServletRequest request, HttpServletResponse response) {

		userRepository.save(user);

		return ResponseEntity.ok(true);
	}
}