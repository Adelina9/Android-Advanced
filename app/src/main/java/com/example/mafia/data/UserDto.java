package com.example.mafia.data;

public class UserDto {
    private final String name;
    private final int age;

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
