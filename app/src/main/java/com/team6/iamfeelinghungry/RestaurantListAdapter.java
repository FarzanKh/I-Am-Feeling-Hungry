package com.team6.iamfeelinghungry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private List<Business> businessList;
    private Context context;

    public RestaurantListAdapter(List<Business> businessList, Context context) {
        this.businessList = businessList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImg;
        TextView tvTitle;
        TextView tvCategory;
        RatingBar ratingBar;
        TextView tvAddress;
        TextView tvDistance;
        TextView tvPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantImg = itemView.findViewById(R.id.restaurantImg);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
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
