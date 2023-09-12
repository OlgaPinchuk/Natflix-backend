package com.novare.natflix.models.user;

public enum UserType {
    ADMIN(1),
    CUSTOMER(2);

    private final int typeValue;

    UserType(int typeValue) {
        this.typeValue = typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }

}
