package com.example.mafia.data.remote;

import android.util.Log;

import com.example.mafia.data.UserDto;
import com.example.mafia.domain.UsersRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

public class RemoteDataSource implements UsersRepository {
    private static final String TAG = "remote-source";
    private final UserApi api;

    public RemoteDataSource(UserApi api) {
        this.api = api;
    }

    @Override
    public List<UserDto> getUsers() {
        try {
            Response<List<UserDto>> response = api.getUsers().execute();
            Log.wtf(TAG, response.toString());
            return response.body();
        } catch (IOException e) {
            Log.w(TAG, "Something went wrong", e);
            return Collections.emptyList();
        }
    }
}
