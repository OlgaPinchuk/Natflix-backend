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

    public static UserType fromTypeValue(int type) {
        for(UserType userType : UserType.values()) {
            if(userType.getTypeValue() == type) {
                return userType;
            }
        }
        return  null;
    }

}
