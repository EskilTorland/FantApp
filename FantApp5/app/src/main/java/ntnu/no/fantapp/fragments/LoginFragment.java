package ntnu.no.fantapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import ntnu.no.fantapp.ApiClient;
import ntnu.no.fantapp.R;
import ntnu.no.fantapp.UserPrefs;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
   private EditText editName;
   private EditText editPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        editName = view.findViewById(R.id.editTextNameOnLogin);
        editPassword = view.findViewById(R.id.editTextPasswordOnLogin);

        view.findViewById(R.id.loginbutton).setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbutton:
                userLogin();
                break;
        }
    }
    private void userLogin() {
        String uid = editName.getText().toString().trim();
        String pwd = editPassword.getText().toString().trim();

        final UserPrefs userPrefs = new UserPrefs(getContext());

        if (uid.isEmpty()) {
            editName.setError("Please fill in a name");
            editName.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            editPassword.setError("Please fill in a password");
            editPassword.requestFocus();
            return;
        }
        Call<ResponseBody> call = ApiClient.getSingleton().getApi().loginUser(uid,pwd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful()){
                    try {
                        Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_LONG).show();
                        userPrefs.setToken(response.body().string());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
