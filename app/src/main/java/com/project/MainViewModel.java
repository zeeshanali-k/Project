package com.project;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.models.User;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private final FirebaseFirestore firebaseFirestore;
    public MutableLiveData<UsersState> usersState = new MutableLiveData<>();

    public MainViewModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        getUsers();
    }

    public void getUsers() {
        usersState.setValue(new UsersState(true));
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "getUsers: " + task.isSuccessful());
                    Log.d(TAG, "getUsers: " + task.getResult().getDocumentChanges()
                            .size());
                    if (task.isSuccessful() && task.getResult() != null) {
                        Log.d(TAG, "getUsers: " + task.getResult().toObjects(User.class));
                        usersState.postValue(new UsersState(true, task.getResult().toObjects(User.class)));
                    } else {
                        usersState.postValue(new UsersState("No Users Found", false));
                    }
                });
    }

}

