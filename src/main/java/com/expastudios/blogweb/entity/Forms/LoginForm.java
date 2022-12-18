/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity.Forms;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginForm {

    private String username;

    private String password;

}