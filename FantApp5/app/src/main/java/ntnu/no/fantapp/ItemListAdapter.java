package ntnu.no.fantapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ntnu.no.fantapp.fragments.AddItemFragment;
import ntnu.no.fantapp.fragments.ItemListFragment;
import ntnu.no.fantapp.fragments.LoginFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder>{

    private ArrayList<Item> itemList = new ArrayList<>();
    private Context context;
    ItemListFragment itemListFragment;
    public ItemListAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemTitle.setText(itemList.get(position).getTitle());
        holder.itemDescription.setText(itemList.get(position).getDescription());
        holder.itemPrice.setText("Price " + itemList.get(position).getPrice() + " kr");
        holder.imageView.setImageResource(R.drawable.ic_browse);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.myDialog));
                builder1.setMessage("Do you want to buy this item?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                purchaseItem(position);
                                System.out.println("KUKK");
                                dialog.cancel();
                            }
                        }
                );

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public void setItemListFragment(ItemListFragment itemListFragment){
        this.itemListFragment = itemListFragment;
    }
    public void setItemList(ArrayList<Item> itemList){
        ArrayList<Item> updatedItemList = new ArrayList<>();
        for (Item item:itemList
             ) {
            if(item.isSold()==false){
                updatedItemList.add(item);
            }
        }
        this.itemList = updatedItemList;
        notifyDataSetChanged();
    }

    private Long getId (int position){
        return itemList.get(position).getId();
    }

    private void purchaseItem(final int position){
        UserPrefs userPrefs = new UserPrefs(context);
        final String token = "Bearer " + userPrefs.getToken();

        Call<ResponseBody> call = ApiClient.getSingleton().getApi().purchaseItem(token,getId(position));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Item bought",Toast.LENGTH_LONG).show();
                    itemListFragment.setItemList();

                }else{
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView itemTitle;
        private TextView itemDescription;
        private TextView itemPrice;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemTitle = itemView.findViewById(R.id.title);
            itemDescription = itemView.findViewById(R.id.description);
            itemPrice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.image);
            linearLayout = itemView.findViewById(R.id.item);

        }
    }
}

