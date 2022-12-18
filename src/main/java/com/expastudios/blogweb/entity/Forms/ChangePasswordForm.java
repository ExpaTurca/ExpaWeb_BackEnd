/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity.Forms;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangePasswordForm {

    private String currentPassword;

    private String newPassword;

}