package com.example.mafia.data;

import com.example.mafia.domain.UsersRepository;

import java.util.Arrays;
import java.util.List;

public class InMemoryDataSource implements UsersRepository {
    private List<UserDto> usersDtos;

    @Override
    public List<UserDto> getUsers() {
        usersDtos = Arrays.asList(
                new UserDto("Adelina", 22),
                new UserDto("Dan", 22),
                new UserDto("Ion", 22)
        );
        return usersDtos;
    }
}
