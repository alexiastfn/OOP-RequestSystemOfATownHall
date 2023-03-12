package org.example.Models;

public class PublicClerk<T> {
    private String name;

    public PublicClerk(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PublicClerk<T> setName(String name) {
        this.name = name;
        return this;
    }
}
