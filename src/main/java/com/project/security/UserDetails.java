package com.project.security;

public class UserDetails {

    private Integer id;
    private String email;
//    private Role role;

    public UserDetails() {
    }

    public UserDetails(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
