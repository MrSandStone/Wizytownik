package com.bielskiAdam.wizytownik;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bielskiAdam.wizytownik.DataBaseModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataBaseModel.DataConverter;
import com.bielskiAdam.wizytownik.DataBaseModel.DatabaseFactory;

import java.util.ArrayList;
import java.util.List;

public class BusinessCardRecycler extends RecyclerView.Adapter<BusinessCardViewHolder> implements Filterable {

    private Context context;
    private ArrayList<BusinessCard> listCards;
    private ArrayList<BusinessCard> mArrayList;
    private DatabaseFactory mDatabase;

    BusinessCardRecycler(Context context, ArrayList<BusinessCard> listCards){
        this.context = context;
        this.listCards = listCards;
        this.mArrayList = listCards;
        mDatabase = new DatabaseFactory(context);
    }

    @Override
    public BusinessCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_iteam_layout, viewGroup, false);
        return new BusinessCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessCardViewHolder holder, int position) {
        final BusinessCard businessCard = listCards.get(position);
        holder.title.setText(businessCard.getTitle());
        holder.phoneNumber.setText(businessCard.getPhoneNumber());
        holder.address.setText(businessCard.getAddress());
        holder.description.setText(businessCard.getDescription());
        holder.imageView.setImageBitmap(DataConverter.convertByteArrayToBitmap(businessCard.getImage()));

        holder.cardButtonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editCard(businessCard);
            }
        });

        holder.cardButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteCard(businessCard.getId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    listCards = mArrayList;
                } else{
                    ArrayList<BusinessCard> filteredList = new ArrayList<>();
                    for(BusinessCard businessCard : mArrayList){
                        if (businessCard.getTitle().toLowerCase().contains((charString))) {
                            filteredList.add(businessCard);
                        }
                    }
                    listCards = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listCards;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listCards = (ArrayList<BusinessCard>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listCards.size();
    }

    private void editCard(final BusinessCard businessCard) {

    }



}
