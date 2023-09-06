package com.example.newregiform;

public class AdminModel {

    public String email;
    public String id;

    public AdminModel(String email, String id) {

        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
