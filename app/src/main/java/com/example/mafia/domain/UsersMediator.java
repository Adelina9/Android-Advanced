package com.example.mafia.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.data.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsersMediator {
    private static final String TAG = "mediator";
    private final UsersRepository localRepository;
    private final UsersRepository remoteRepository;
    private final ExecutorService executorService;
    private final MutableLiveData<List<User>> liveUsers;

    public UsersMediator(UsersRepository localRepository, UsersRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.executorService = Executors.newSingleThreadExecutor();
        this.liveUsers = new MutableLiveData<>();
    }

    public LiveData<List<User>> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (UserDto dto : localRepository.getUsers()) {
            users.add(new User(dto.getName(), dto.getAge()));
        }

        executorService.execute(() -> {

            List<UserDto> dtos = remoteRepository.getUsers();
            if (dtos != null) {
                for (UserDto dto : dtos) {
                    users.add(new User(dto.getName(), dto.getAge()));
                }
            }

            this.liveUsers.postValue(users);
        });

        return liveUsers;
    }
}
