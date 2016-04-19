package com.example.rasmusengmark.cardmanager;

/**
 * Created by Mikkel on 19-04-2016.
 */
public class User {

    private Long _id;
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }
}
