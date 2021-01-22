package com.project.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserDto {

    @Id
    @NotNull
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime updated;


    @PrePersist
    public void onPrePersist() {
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }
    @PreUpdate
    public void onPreUpdate() {
        updated = LocalDateTime.now();
    }
    public UserDto() {
    }

    public UserDto(String fullName, String email, String password, LocalDateTime created, LocalDateTime updated) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.created = created;
        this.updated = updated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
