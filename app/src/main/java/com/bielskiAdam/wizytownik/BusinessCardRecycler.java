package com.bielskiAdam.wizytownik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bielskiAdam.wizytownik.DataModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataModel.DataConverter;

import java.util.List;

public class BusinessCardRecycler extends RecyclerView.Adapter<BusinessCardViewHolder> {

    List<BusinessCard> data;
    public BusinessCardRecycler (List<BusinessCard> businessCards){
        data = businessCards;
    }
    @NonNull
    @Override
    public BusinessCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.card_iteam_layout,
                viewGroup,
                false
        );
        return new BusinessCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessCardViewHolder holder, int position) {
        BusinessCard businessCard = data.get(position);
        holder.imageView.setImageBitmap(DataConverter.converByteArray2Image(businessCard.getImage()));
        holder.title.setText(businessCard.getTitle());
        holder.phoneNumber.setText(businessCard.getPhoneNumber());
        holder.address.setText(businessCard.getAddress());
        holder.description.setText(businessCard.getDescription());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
