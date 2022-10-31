/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.Util;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;

@RequiredArgsConstructor
public class Zone {
	
	public static LocalDateTime getCurrentTime ( ) {
		
		System.out.println ( "Zaman: " + LocalDateTime.now ( ) );
		return LocalDateTime.now ( );
	}
	
	public static String getLocaleZone ( HttpServletRequest request ) {
		
		Locale local = request.getLocale ( );
		return local.getLanguage ( );
	}
	
}
