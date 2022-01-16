package com.project.authentication.register;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.authentication.UIState;
import com.project.models.User;

public class RegisterViewModel extends ViewModel {

    public final MutableLiveData<UIState> registerState = new MutableLiveData<>();

    private final FirebaseFirestore firebaseFirestore;

    public RegisterViewModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private static final String TAG = "RegisterViewModel";
    public void registerUser(User user) {
        registerState.setValue(new UIState(true, false));
        firebaseFirestore.collection("users")
                .add(user).addOnCompleteListener(task -> {
            Log.d(TAG, "registerUser: "+task.getResult().toString());
            if (task.isSuccessful() && task.getResult()!=null) {
                registerState.postValue(new UIState(false, true));
            } else
                registerState.postValue(new UIState(false, false, "Email or Password is not correct"));
        });
    }
}
