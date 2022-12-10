/***************************************************************
 * Copyright (c) 2022
 **************************************************************/




package com.expastudios.blogweb.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public final class PasswordEncryptor {
	
	public static String Encrypt ( String rawPassword ) {
		
		return new BCryptPasswordEncoder ( ).encode ( rawPassword );
	}
}
