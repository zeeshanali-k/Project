package com.project.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.databinding.RvUserItemBinding;
import com.project.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    private List<User> userList;

    public UsersAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersHolder(
                RvUserItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,false
                )
        );
    }

    private static final String TAG = "UsersAdapter";
    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.rvBinding.setUser(userList.get(position));
        Log.d(TAG, "onBindViewHolder: "+userList.get(position).getProfileImg());
        Glide.with(holder.rvBinding.getRoot())
                .load(userList.get(position).getProfileImg())
                .centerCrop()
                .into(holder.rvBinding.userImg);
        holder.rvBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UsersHolder extends RecyclerView.ViewHolder {
        private RvUserItemBinding rvBinding;
        public UsersHolder(@NonNull RvUserItemBinding itemView) {
            super(itemView.getRoot());
            rvBinding = itemView;
        }
    }
}
