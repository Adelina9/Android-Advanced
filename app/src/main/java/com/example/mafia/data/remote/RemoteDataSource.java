package com.example.mafia.data.remote;

import com.example.mafia.data.UserDto;
import com.example.mafia.domain.UsersRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;
import timber.log.Timber;

public class RemoteDataSource implements UsersRepository {
    private final UserApi api;

    public RemoteDataSource(UserApi api) {
        this.api = api;
    }

    @Override
    public List<UserDto> getUsers() {
        try {
            Response<List<UserDto>> response = api.getUsers().execute();
            Timber.wtf(response.toString());
            return response.body();
        } catch (IOException e) {
            Timber.w("Something went wrong %s", e);
            return Collections.emptyList();
        }
    }
}
