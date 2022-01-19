package com.project.authentication.register;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.project.MainActivity;
import com.project.R;
import com.project.databinding.ActivityRegisterBinding;
import com.project.models.User;

import java.io.FileNotFoundException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;
    private ActivityResultLauncher<String> imgPickerLauncher;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this)
                .get(RegisterViewModel.class);
        binding.btnRegister.setOnClickListener(v -> {
            registerUser();
        });
        setUpObserver();
        binding.selectImgBtn.setOnClickListener(v -> {
            imgPickerLauncher.launch("image/*");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        imgPickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            imgUri = result;
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imgUri));
                binding.selectImgBtn.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {

                Log.d(TAG, "onStart: " + e.getCause());
                Log.d(TAG, "onStart: " + e.getLocalizedMessage());
            }
        });
    }

    private void setUpObserver() {
        registerViewModel.registerState.observe(this, uiState -> {
            binding.setIsLoading(uiState.isLoading());
            if (uiState.isLoading()) return;
            if (uiState.isAuthenticated()) {
                getSharedPreferences("project_prefs", MODE_PRIVATE)
                        .edit().putBoolean("is_logged_in", true).apply();
                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT)
                        .show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), uiState.getError(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void registerUser() {
        if (binding.registerEmail.getText().toString().isEmpty()) {
            binding.registerEmail.setError(getString(R.string.email_error));
        } else if (binding.registerPassword.getText().toString().isEmpty()) {
            binding.registerPassword.setError(getString(R.string.password_error));
        } else if (binding.registerFirstName.getText().toString().isEmpty()) {
            binding.registerFirstName.setError(getString(R.string.password_error));
        } else if (imgUri == null || imgUri.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        } else {
            try {
                registerViewModel.uploadImageAndRegister(getContentResolver().openInputStream(imgUri),
                        new User(
                                binding.registerEmail.getText().toString(),
                                binding.registerPassword.getText().toString(),
                                binding.registerFirstName.getText().toString()
                        ));
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onStart: " + e.getCause());
                Log.d(TAG, "onStart: " + e.getLocalizedMessage());
            }
        }
    }
}