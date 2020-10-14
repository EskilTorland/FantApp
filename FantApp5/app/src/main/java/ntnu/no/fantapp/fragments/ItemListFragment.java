package ntnu.no.fantapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import ntnu.no.fantapp.ApiClient;
import ntnu.no.fantapp.Item;
import ntnu.no.fantapp.ItemListAdapter;
import ntnu.no.fantapp.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Item> itemList = new ArrayList<>();
    private ItemListAdapter adapter;
    private RecyclerView itemRecyclerView;
    //private RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        itemRecyclerView = view.findViewById(R.id.recyclerView);

        adapter = new ItemListAdapter(getContext());
        adapter.setItemListFragment(this);
        adapter.setItemList(itemList);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        setItemList();


        return view;
    }

    public void setItemList() {
        Call<List<Item>> call = ApiClient.getSingleton().getApi().getAllItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    itemList = (ArrayList<Item>) response.body();
                    System.out.println(response.body());
                    adapter.setItemList(itemList);
                } else {
                    Toast.makeText(getContext(), "No items fetched", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}