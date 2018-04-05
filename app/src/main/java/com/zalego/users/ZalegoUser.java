package com.zalego.users;

/**
 * Created by edith on 05/04/2018.
 */
public class ZalegoUser {

    public String firstname,email,gender,language,dob,password;

    public String getDob() {
        return dob;
    }

    public ZalegoUser setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ZalegoUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public ZalegoUser setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public ZalegoUser setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public ZalegoUser setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ZalegoUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
