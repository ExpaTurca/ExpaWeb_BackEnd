package com.expastudios.blogweb.config;

public class RegularExpression {
	public static final String LOGIN_REGEX = "^(?=.*[a-zA-Z0-9\\.\\-\\_]{3,24}$).*";
	public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z0-9.-_+%&\\/\\(\\)\\[\\]\\{\\}]{8,16}$).* ";
	public static final String SYSTEM = "system";
	public static final String DEFAULT_LANGUAGE = "en";
}
