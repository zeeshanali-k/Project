package com.project.authentication.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.project.MainActivity;
import com.project.R;
import com.project.authentication.register.RegisterActivity;
import com.project.databinding.ActivityLoginBinding;
import com.project.models.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedPreferences =
                getSharedPreferences("project_prefs", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("is_logged_in",false)){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return;
        }
        binding.btnLogin.setOnClickListener(v -> loginUser());
        binding.loginNotSignedUp
                .setOnClickListener(v ->
                        startActivity(new Intent(this,
                                RegisterActivity.class)));
        setUpObserver();
    }

    private void setUpObserver() {
        loginViewModel.loginState.observe(this, uiState -> {
            binding.setIsLoading(uiState.isLoading());
            if (uiState.isLoading()) return;
            if (uiState.isAuthenticated()) {
                sharedPreferences
                        .edit().putBoolean("is_logged_in", true)
                        .apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), uiState.getError(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginUser() {
        if (binding.loginUsername.getText().toString().isEmpty()) {
            binding.loginUsername.setError(getString(R.string.email_error));
        } else if (binding.loginPassword.getText().toString().isEmpty()) {
            binding.loginPassword.setError(getString(R.string.password_error));
        } else {
            loginViewModel.loginUser(new User(
                    binding.loginUsername.getText().toString(),
                    binding.loginPassword.getText().toString()
            ));
        }
    }
}