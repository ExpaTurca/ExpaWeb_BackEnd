/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.services;


import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Account;
import com.expastudios.blogweb.entity.DTOs.AccountDTO;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final ModelMapper mapper;

    private final AccountRepository accountRepository;

    public List<AccountDTO> getAll() {
        return accountRepository.findByIsActiveTrue().stream().map(map -> mapper.map(map, AccountDTO.class)).toList();
    }

    public Page<AccountDTO> getAll(int pageNumber, int size) {
        Pageable page = PageRequest.of(pageNumber, size);
        List<AccountDTO> list = accountRepository.findByIsActiveTrueOrderByLastNameAsc(page)
                .stream().map(map -> mapper.map(map, AccountDTO.class)).collect(Collectors.toList());
        int total = list.size();
        return new PageImpl<>(list, page, total);
    }

    public AccountDTO getById(UUID Id) {
        return mapper.map(accountRepository.findByIdAndIsActiveTrue(Id).orElseThrow(() -> {
            throw new RuntimeException("Account not found!");
        }), AccountDTO.class);
    }

    protected Account getByEmail(String email) {

        return accountRepository.findByEmailAndIsActiveTrue(email).orElseThrow(() -> {
            throw new RuntimeException("Account not found!");
        });
    }

    public String create(NewUserForm userForm
    ) {

        Account account = new Account();
        account.setFirstName(userForm.getFirstName());
        account.setLastName(userForm.getLastName());
        account.setEmail(userForm.getEmail());
        account.setUsername(userForm.getUsername());
        account.setPassword(userForm.getPassword());
        account.setRegisteredAt(Zone.getCurrentTime());
        account.setActive(true);

        try {
            accountRepository.save(account);
            return "success-message";
        } catch (HibernateException exc) {
            throw new RuntimeException(exc.getLocalizedMessage());
        }
    }

    public String edit(Account account, HttpServletRequest request, HttpServletResponse response) {
        if (accountRepository.findByIdAndIsActiveTrue(account.getId()).isPresent()) {
            accountRepository.updateUser(
                    account.getFirstName(),
                    account.getLastName(),
                    account.getPassword(),
                    account.getUsername(),
                    account.getId(),
                    account.isActive()
            );
        }
        try {
            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }
}