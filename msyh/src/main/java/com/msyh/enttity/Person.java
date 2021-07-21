package com.msyh.enttity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 13778
 */
@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = true, length = 20)
    private String name;

    @Column(name = "age", nullable = true, length = 20)
    private String age;

    @Column(name = "phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "mail", nullable = true, length = 20)
    private String mail;

    @Column(name = "address", nullable = true, length = 20)
    private String address;

    @Column(name = "hobby", nullable = true, length = 20)
    private String hobby;

    @Column(name = "station", nullable = true, length = 20)
    private String station;

    @Column(name = "birthday", nullable = true, length = 20)
    private String birthday;

}
