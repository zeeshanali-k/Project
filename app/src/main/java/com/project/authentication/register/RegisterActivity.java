package com.project.authentication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.project.MainActivity;
import com.project.R;
import com.project.databinding.ActivityRegisterBinding;
import com.project.models.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this)
                .get(RegisterViewModel.class);
        binding.btnRegister.setOnClickListener(v->{
            registerUser();
        });
        setUpObserver();
    }

    private void setUpObserver() {
        registerViewModel.registerState.observe(this, uiState -> {
            binding.setIsLoading(uiState.isLoading());
            if (uiState.isLoading()) return;
            if(uiState.isAuthenticated()){
                getSharedPreferences("project_prefs",MODE_PRIVATE)
                        .edit().putBoolean("is_logged_in",true).apply();
                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), uiState.getError(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void registerUser() {
        if(binding.registerEmail.getText().toString().isEmpty()){
            binding.registerEmail.setError(getString(R.string.email_error));
        }else if (binding.registerPassword.getText().toString().isEmpty()) {
            binding.registerPassword.setError(getString(R.string.password_error));
        }else if (binding.registerFirstName.getText().toString().isEmpty()) {
            binding.registerFirstName.setError(getString(R.string.password_error));
        }else{
            registerViewModel.registerUser(new User(
                    binding.registerEmail.getText().toString(),
                    binding.registerPassword.getText().toString(),
                    binding.registerFirstName.getText().toString()
            ));
        }
    }
}