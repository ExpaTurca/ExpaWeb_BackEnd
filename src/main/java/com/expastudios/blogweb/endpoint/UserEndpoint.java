/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.Account;
import com.expastudios.blogweb.entity.DTOs.AccountDTO;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/user", consumes = {"application/xml", "application/json"})
@RequiredArgsConstructor
public class UserEndpoint {

    private final AccountService accountService;

    @GetMapping(value = "/getall")
    public Page<AccountDTO> getall(@PathParam(value = "pg") int pg, @PathParam(value = "sz") int sz, HttpServletRequest request, HttpServletResponse response) {
        return accountService.getAll(pg, sz);
    }

    @GetMapping(value = "/{Id}")
    public AccountDTO Index(@PathVariable UUID Id,
                            HttpServletRequest request,
                            HttpServletResponse response) throws ClassNotFoundException {

        return accountService.getById(Id);
    }

    @PostMapping(value = "/create")
    public String NewUser(@RequestBody NewUserForm userForm) throws ClassNotFoundException {

        return accountService.create(userForm);
    }

    @PostMapping(value = "/edit")
    public String EditUser(@RequestBody AccountDTO accountDTO,
                           HttpServletRequest request,
                           HttpServletResponse response) throws ClassNotFoundException {

        return accountService.edit((Account) EntityDtoConversion.ConvertToEntity(accountDTO), request, response);
    }
}