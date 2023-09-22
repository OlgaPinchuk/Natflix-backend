package com.novare.natflix.models.user;

public class UserRegisterInput {
    private String name;
    private String email;
    private String password;
    private int type;

    public UserRegisterInput() {}


    public UserRegisterInput(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRegisterInput(String name, String email, String password, int type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserType getTypeEnum() {
        if(type == UserType.ADMIN.getTypeValue()) {
            return UserType.ADMIN;
        }
        else if(type == UserType.CUSTOMER.getTypeValue()) {
            return UserType.CUSTOMER;
        }
        else {
            return null;
        }
    }
}
