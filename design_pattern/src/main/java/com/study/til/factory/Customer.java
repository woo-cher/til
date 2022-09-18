package com.study.til.factory;

public enum Customer {
    COLD("가레가 끼고, 목이 따끔해요"),
    DIGESTIVE("밥 먹고 체한 것 같아요"),
    HEADACHE("머리가 아파요");

    public String message;

    Customer(String message) {
        this.message = message;
    }

    public static Customer create(String value) {
        for (Customer type : Customer.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        return null;
    }
}
