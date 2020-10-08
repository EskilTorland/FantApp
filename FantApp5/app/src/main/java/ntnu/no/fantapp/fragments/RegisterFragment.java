package ntnu.no.fantapp.fragments;

import android.os.Bundle;
import android.util.Patterns;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editName = view.findViewById(R.id.editTextName);
        editPassword = view.findViewById(R.id.editTextPassword);
        editEmail = view.findViewById(R.id.editTextEmail);

        view.findViewById(R.id.regButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regButton:
                userRegister();
                break;
        }
    }

    private void userRegister() {
        String uid = editName.getText().toString().trim();
        String pwd = editPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Please fill in an email");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email is invalid");
            editEmail.requestFocus();
            return;
        }
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
        if (pwd.length() < 3) {
            editPassword.setError("Sorry, your password must contain at least 13 characters, and contain at least one number, which cannot be any of the first six digits of pi, and cannot be your phone number, eldest sibling's birthday, or your lotto numbers. You must have at least two of the following characters: Я, Δ, \uD83C\uDF4D, or \uD83D\uDC07. You may not repeat characters more than three times in a row, unless you are Christian, in which case you may use 777 or if you are a member of the Church of Satan you may use 666. Should you choose a person emoji in your password, it cannot represent your own skin color. Your password cannot be one of your last seventeen passwords, nor can it include your first or second pet's name (you may include the names of goldfish that died within two days of coming home from the fair). You may not include the name of any professional sports team that has won a national title in the past six years. You may not include any words found in the Official Scrabble Players Dictionary, including the dumb two-letter ones that are ridiculous, like \"AA\". You may not include Klingon words that have actually been uttered in an official Star Trek franchise show or film. Likewise for Elvish words in the Hobbit or in the first two Lord of the Rings books. You cannot use any word that sounds like, looks like, or seems like the word \"Love\" (e.g., Luv, Luhvs, hearts, L0V, etc.). All consecutive letters must alternate between upper and lower case in a pattern matching the Fibonacci series (e.g., AbCDefgHIJKL). Do not include any letters highlighted on Sesame Street in the past six months. Do not write down your password with any visible ink, nor on any electronic device, unless for the purposes of logging into this website. Should you forget your password, you will be required to request a password reset in person in our offices at Huffman Prairie Flying Field, Dayton, Ohio, during regular business hours, except on Friday. There will be a five day waiting period for password resets, which we will send to your email address, which cannot be a .google.com, .hotmail.com, or yahoo.com address.");
            editPassword.requestFocus();
            return;
        }
        Call<ResponseBody> call = ApiClient.getSingleton().getApi().createUser(uid, pwd, email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String s = response.body().string();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    } else {
                        String s = response.errorBody().string();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
