package com.expastudios.blogweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@RequiredArgsConstructor @Getter @Setter
public class ProfileDTO {
    private long id;
    private String fulname;
    private String email;
    private char gender;
    private String profileImage;
    private Timestamp registeredAt;
}
