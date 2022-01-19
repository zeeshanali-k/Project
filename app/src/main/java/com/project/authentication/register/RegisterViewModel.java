package com.project.authentication.register;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.project.authentication.UIState;
import com.project.models.User;

import java.io.IOException;
import java.io.InputStream;

public class RegisterViewModel extends ViewModel {

    public final MutableLiveData<UIState> registerState = new MutableLiveData<>();

    private final FirebaseFirestore firebaseFirestore;

    public RegisterViewModel() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private static final String TAG = "RegisterViewModel";
    public void uploadImageAndRegister(InputStream inputStream,User user){

        registerState.setValue(new UIState(true, false));
        FirebaseStorage.getInstance().getReference("user_images/"+System.currentTimeMillis()+".jpeg")
                .putStream(inputStream).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(result->{
                            Log.d(TAG, "uploadImageAndRegister: "+result.toString());
                            user.setProfileImg(result.toString());
                            registerUser(user);
                        });
                    }else{
                        Log.d(TAG, "uploadImageAndRegister: "+task.getException().getLocalizedMessage());
                        Log.d(TAG, "uploadImageAndRegister: "+task.getException().getCause());
                        registerState.postValue(new UIState(false, false, "Email or Password is not correct"));
                    }
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.d(TAG, "uploadImageAndRegister: "+e.getLocalizedMessage());
            }
        });
    }

    private void registerUser(User user) {
        firebaseFirestore.collection("users")
                .add(user).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult()!=null) {
                registerState.postValue(new UIState(false, true));
            } else
                registerState.postValue(new UIState(false, false, "Email or Password is not correct"));
        });
    }
}
