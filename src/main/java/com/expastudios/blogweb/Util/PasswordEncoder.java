/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.Util;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter @Setter @RequiredArgsConstructor
public class PasswordEncoder {
	
	
	public static String encode ( CharSequence rawPassword ) {
		
		final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder ( );
		return bCryptPasswordEncoder.encode ( rawPassword );
	}
	
}
