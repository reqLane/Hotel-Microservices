package com.naukma.clientservice.role;

public enum Role {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
