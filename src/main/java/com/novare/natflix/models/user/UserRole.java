package com.novare.natflix.models.user;

public enum UserRole {
    CUSTOMER("Customer"),
    ADMIN("Admin");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
