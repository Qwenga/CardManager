package com.example.rasmusengmark.cardmanager;

/**
 * Created by Mikkel on 19-04-2016.
 */
public class User {

    private Long _id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int age;
    private String cpr;


    public Long getId() {return _id;}

    public void setId(Long id) {this._id = id;}

    public String getEmail() {return email; }

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) { this.age = age; }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }


}
