package com.example.mafia.presentation;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.domain.User;

import java.util.List;

public class CreateRoomAdapter {
    @BindingAdapter("numbersOfPlayersRes")
    public static void setTitle(TextView textView, @StringRes int numbersOfPlayersRes) {
        Context context = textView.getContext();
        textView.setText(context.getString(numbersOfPlayersRes));
    }

    @BindingAdapter("users")
    public static void setItems(RecyclerView recyclerView, List<User> users) {
        RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new UsersAdapter();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }
        if (users != null) {
            ((UsersAdapter) adapter).updateUsers(users);
        }
    }
}
