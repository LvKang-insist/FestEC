package com.diabin.latte.ec.mytable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Copyright (C)
 *
 * @file: UserProfile
 * @author: 345
 * @Time: 2019/4/22 19:11
 * @description: ${DESCRIPTION}
 */

/**
 * nameInDb: 表名
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private String name = null;
    private String email = null;
    private String phone;
    private String password;
    @Generated(hash = 1493368741)
    public UserProfile(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
