/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByIsActiveTrue();

    Page<Account> findByIsActiveTrueOrderByLastNameAsc(Pageable pageable);


    @Modifying
    @Query("""
            update e_user e set e.firstName = ?1, e.lastName = ?2, e.password = ?3, e.username = ?4
            where e.id = ?5 and e.isActive = ?6""")
    int updateUser(String firstName, String lastName, String password, String username, UUID id, boolean isActive);

    Optional<Account> findByIdAndIsActiveTrue(UUID Id);

    Optional<Account> findByEmailAndIsActiveTrue(String email);

    Optional<Account> findByUsernameAndIsActiveTrue(String username);

}
