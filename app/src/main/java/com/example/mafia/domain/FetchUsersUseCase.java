package com.example.mafia.domain;

import androidx.lifecycle.LiveData;

import com.example.mafia.data.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchUsersUseCase {
    private final UsersMediator mediator;

    public FetchUsersUseCase(UsersMediator mediator) {
        this.mediator = mediator;
    }

    public LiveData<List<User>> execute() {
        return mediator.getUsers();
    }
}
