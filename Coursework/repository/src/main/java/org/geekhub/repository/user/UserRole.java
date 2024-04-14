package org.geekhub.repository.user;

public class UserRole {
    private Integer id;
    private String role;

    public UserRole() {
    }

    public UserRole(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
