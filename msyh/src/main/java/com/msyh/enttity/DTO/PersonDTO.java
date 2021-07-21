package com.msyh.enttity.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 * @author 13778
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("person")
public class PersonDTO {


    @Id
    @TableField(value = "id")
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "age")
    private String age;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "mail")
    private String mail;

    @TableField(value = "address")
    private String address;

    @TableField(value = "hobby")
    private String hobby;

    @TableField(value = "station")
    private String station;

    @TableField(value = "birthday")
    private String birthday;

}