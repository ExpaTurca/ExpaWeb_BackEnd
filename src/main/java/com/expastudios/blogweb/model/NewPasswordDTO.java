package com.expastudios.blogweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NewPasswordDTO {
	private String current_password;
	private String new_password;
}
