package com.telran.classwork.hairsalon2.Models;

import java.util.ArrayList;

/**
 * Created by vadim on 15.03.2017.
 */

public class Master {
    private String name, lastname, email, phone, password, reTypePassword;

    public ArrayList<MasterLanguage> getLangs() {
        return langs;
    }

    public void addLangs(MasterLanguage lang) {
        langs.add(lang);
    }

    private ArrayList<MasterLanguage> langs = new ArrayList<>();




    public Master() {
    }

    public Master(String name, String lastname, String email, String phone, String password, String reTypePassword) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.reTypePassword = reTypePassword;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReTypePassword() {
        return reTypePassword;
    }

    public void setReTypePassword(String reTypePassword) {
        this.reTypePassword = reTypePassword;
    }

}
