package com.diabin.latte.ec.mytable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright (C)
 *
 * @file: Person
 * @author: 345
 * @Time: 2019/4/23 13:24
 * @description: ${DESCRIPTION}
 */
@Entity(nameInDb = "person")
public class Person {
    String name;
    int age;
    @Generated(hash = 1687962320)
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

}
