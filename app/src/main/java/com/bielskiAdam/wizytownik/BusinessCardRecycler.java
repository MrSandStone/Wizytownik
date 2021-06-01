package com.bielskiAdam.wizytownik;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bielskiAdam.wizytownik.DataBaseModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataBaseModel.DataConverter;
import com.bielskiAdam.wizytownik.DataBaseModel.DatabaseFactory;

import java.util.ArrayList;
import java.util.Objects;

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
            public void onClick(View view) {
                editCard(businessCard);
            }
        });

        holder.cardButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_create_new, null);

        final EditText titleField = subView.findViewById(R.id.editTextTitleBusinessCard);
        final EditText phoneField = subView.findViewById(R.id.editTextPhone);
        final EditText addressField = subView.findViewById(R.id.editTextTextAddress);
        final EditText descritpionField = subView.findViewById(R.id.editTextTextDescription);
        final ImageView imageField = subView.findViewById(R.id.selectedImage);

        if(businessCard != null) {
            titleField.setText(businessCard.getTitle());
            phoneField.setText(String.valueOf(businessCard.getPhoneNumber()));
            addressField.setText(businessCard.getAddress());
            descritpionField.setText(businessCard.getDescription());
            //imageField.setImageBitmap(DataConverter.convertByteArrayToBitmap(businessCard.getImage()));
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edycja");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Edycja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String title = titleField.getText().toString();
                final String phone = phoneField.getText().toString();
                final String address = addressField.getText().toString();
                final String description = descritpionField.getText().toString();
                final byte[] image = DataConverter.convertImageViewToByteArray(imageField);


                if(TextUtils.isEmpty(title)){
                    Toast.makeText(
                            context,
                            "Sprawdź poprawność wartości",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    mDatabase.updateCard(new BusinessCard(Objects.requireNonNull(businessCard).getId(), title, phone, address, description, image));
                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Anulowano", Toast.LENGTH_LONG).show();
            }
        });
    }
}
