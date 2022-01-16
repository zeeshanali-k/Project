package com.project.authentication.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.authentication.UIState;
import com.project.models.User;

public class LoginViewModel extends ViewModel {

    public final MutableLiveData<UIState> loginState = new MutableLiveData<>();

    private final FirebaseFirestore firebaseFirestore;

    public LoginViewModel(){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void loginUser(User user){
        loginState.setValue(new UIState(true,false));
        firebaseFirestore.collection("users")
                .whereEqualTo("email",user.getEmail())
                .whereEqualTo("password",user.getPassword()).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful() && !task.getResult().isEmpty()){
                        loginState.postValue(new UIState(false,true));
                    }else
                        loginState.postValue(new UIState(false,false,"Email or Password is not correct"));
                });
    }


}
