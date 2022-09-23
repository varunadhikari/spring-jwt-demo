package com.jwt.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

}

