package com.msyh.enttity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 13778
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account {
    @Id
    @TableField(value = "id")
    private int id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "perms")
    private String perms;

    @TableField(value = "role")
    private String role;
}
