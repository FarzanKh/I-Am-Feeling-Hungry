package com.team6.iamfeelinghungry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteRestaurantAdapter extends RecyclerView.Adapter<FavoriteRestaurantAdapter.ViewHolder> {
    private List<Restaurant> favoriteRestaurantList;
    private Context context;

    public FavoriteRestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.favoriteRestaurantList = restaurantList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCategory;
        TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAddress = itemView.findViewById(R.id.tvAddress);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant favoriteRestaurant = favoriteRestaurantList.get(position);
        holder.tvTitle.setText(favoriteRestaurant.name);
        holder.tvCategory.setText(favoriteRestaurant.category);
        holder.tvAddress.setText(favoriteRestaurant.address);
    }

    @Override
    public int getItemCount() {
        return favoriteRestaurantList.size();
    }
}


