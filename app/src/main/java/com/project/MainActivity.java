package com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.adapters.UsersAdapter;
import com.project.authentication.login.LoginActivity;
import com.project.databinding.ActivityMainBinding;
import com.project.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setUpObserver();
    }

    private void setUpObserver() {
        mainViewModel.usersState.observe(this, uiState -> {
            binding.setIsLoading(uiState.isLoading());
            if (uiState.isLoading()) return;
            if (uiState.isSuccess()) {
                setUpUsersRv(uiState.getUsers());
            } else {
                Toast.makeText(getApplicationContext(), uiState.getError(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_btn){
            getSharedPreferences("project_prefs",MODE_PRIVATE)
                    .edit().putBoolean("is_logged_in",false)
                    .apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpUsersRv(List<User> userList) {
        binding.usersRv.setLayoutManager(new LinearLayoutManager(this));
        binding.usersRv.setAdapter(new UsersAdapter(userList));
    }

}