package com.team6.iamfeelinghungry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private final ClickListener listener;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    public static List<Business> businessList;
    private Context context;
    private String userId;


    public RestaurantListAdapter(List<Business> businessList, Context context, ClickListener listener) {
        this.businessList = businessList;
        this.context = context;
        firebaseAuth=FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance("https://hungryapp-d791e-default-rtdb.firebaseio.com/").getReference();
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView restaurantImg;
        TextView tvTitle;
        TextView tvCategory;
        RatingBar ratingBar;
        TextView tvAddress;
        TextView tvDistance;
        TextView tvPrice;
        ImageButton tvImgButton;

        private WeakReference<ClickListener> listenerRef;


        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);

            restaurantImg = itemView.findViewById(R.id.restaurantImg);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvImgButton = itemView.findViewById(R.id.tvImgButton);

            listenerRef = new WeakReference<>(listener);
            restaurantImg.setOnClickListener(this);
            tvImgButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == tvImgButton.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED AT = " + String.valueOf(getAdapterPosition()) , Toast.LENGTH_SHORT).show();

                Business business = businessList.get(getAdapterPosition());

                //write to database
                writeNewRestaurant(business.getId(),business.getName(),business.getCategories().get(0).getTitle(),business.getLocation().getAddress1());

                listenerRef.get().onFavoriteClicked(getAdapterPosition());

            } else if (v.getId() == restaurantImg.getId()) {
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                listenerRef.get().onPositionClicked(getAdapterPosition());
            }

        }

        public void writeNewRestaurant(String yelpId, String name, String category, String address){
            // get new restaurant entry key
            String key = mDatabase.child("users").child(userId).child("restaurants").push().getKey();

            // get current timestamp
            Long tsLong = System.currentTimeMillis();
            String timestamp = tsLong.toString();

            // add new restaurant
            Restaurant restaurant = new Restaurant(yelpId,timestamp,name,category,address);
            Map<String,String> restaurantValues = restaurant.toMap();
            Map<String,Object> childUpdates= new HashMap<>();
            childUpdates.put("/users/" + userId + "/restaurants/" + key,restaurantValues);
            mDatabase.updateChildren(childUpdates);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business business = businessList.get(position);
        holder.tvTitle.setText(business.getName());
        holder.tvPrice.setText(business.getPrice());
        holder.ratingBar.setRating((float) business.getRating());
        holder.tvCategory.setText(business.getCategories().get(0).getTitle());
        holder.tvAddress.setText(business.getLocation().getAddress1());
        holder.tvDistance.setText(business.convertDistance());

        Picasso.get().load(business.getImageUrl()).centerCrop().fit().into(holder.restaurantImg);

    }


    @Override
    public int getItemCount() {
        return businessList.size();
    }
}
