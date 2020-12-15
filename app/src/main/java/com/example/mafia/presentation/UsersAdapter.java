package com.example.mafia.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.mafia.R;
import com.example.mafia.databinding.UserBinding;
import com.example.mafia.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends Adapter<UsersAdapter.UsersViewHolder> {
    private final List<User> users;

    public UsersAdapter() {
        this.users = new ArrayList<>();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.user, parent, false);

        return new UsersViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    public void updateUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {
        private final UserBinding binding;

        public UsersViewHolder(@NonNull UserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User model) {
            binding.setModel(model);
        }
    }
}
