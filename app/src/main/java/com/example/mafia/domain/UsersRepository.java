package com.example.mafia.domain;

import com.example.mafia.data.UserDto;

import java.util.List;

public interface UsersRepository {
        List<UserDto> getUsers();
}
