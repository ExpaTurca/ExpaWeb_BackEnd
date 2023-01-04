/***************************************************************
 * Copyright (c) 2023
 **************************************************************/
package com.expastudios.blogweb.entity.Forms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestFromUUID {
    private UUID uuid;
}
