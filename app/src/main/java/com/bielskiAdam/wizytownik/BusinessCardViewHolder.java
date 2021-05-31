package com.bielskiAdam.wizytownik;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class BusinessCardViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title, phoneNumber, address, description;
    Button cardButtonEdit, cardButtonDelete;
    public BusinessCardViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.idCardTitle);
        phoneNumber = itemView.findViewById(R.id.cardPhoneNumber);
        address = itemView.findViewById(R.id.cardAddress);
        description = itemView.findViewById(R.id.cardDescription);
        imageView = itemView.findViewById(R.id.idCardImage);

        cardButtonEdit = itemView.findViewById(R.id.cardButtonEdit);
        cardButtonDelete = itemView.findViewById(R.id.cardButtonDelete);
    }
}
