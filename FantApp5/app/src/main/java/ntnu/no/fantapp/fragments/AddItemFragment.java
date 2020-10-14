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

import ntnu.no.fantapp.ApiClient;
import ntnu.no.fantapp.R;
import ntnu.no.fantapp.UserPrefs;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemFragment extends Fragment implements View.OnClickListener {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_additem,container,false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextPrice = view.findViewById(R.id.editTextPrice);

        view.findViewById(R.id.addButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        addItem();
    }
    public void addItem(){
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();

        UserPrefs userPrefs = new UserPrefs(getContext());
        String token = "Bearer " + userPrefs.getToken();
        System.out.println(token);

        Call<ResponseBody> call = ApiClient.getSingleton().getApi().addItem(token,title,description,price);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    String s = response.body().toString();
                    Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_LONG).show();
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
