package com.qst.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int uid;
    private String username;
    private String password;
    private String email;
    private int age;
    private int sex;
    private String address;
    private int ustatus;
    private int udelete;

    public int getAge() {
        return age;
    }

    public int getUstatus() {
        return ustatus;
    }

    public void setUstatus(int ustatus) {
        this.ustatus = ustatus;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUdelete() {
        return udelete;
    }

    public void setUdelete(int udelete) {
        this.udelete = udelete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User (){
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }

    public User(String username, String password, String email, int age, int sex, String address){
    	this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public User(int uid,String username,String password,String email,int age,int sex,String address){
        super();
    }

    public Map<String, String> validateRegist() {
        Map<String, String> map = new HashMap<String, String>();

        if (username == null || username.trim().isEmpty()) {
            map.put("username.message", "用户名不能为空");
        }

        if (password == null || password.trim().isEmpty()) {
            map.put("password.message", "密码不能为空");
        }

        if (email == null || email.trim().isEmpty()) {
            map.put("email.message", "邮箱不能为空");
        }

        return map;
    }

}
