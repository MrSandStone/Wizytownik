package com.bielskiAdam.wizytownik;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bielskiAdam.wizytownik.DataBaseModel.BusinessCard;
import com.bielskiAdam.wizytownik.DataBaseModel.DataConverter;
import com.bielskiAdam.wizytownik.DataBaseModel.DatabaseFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public BusinessCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
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

        holder.cardButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLayout(holder.layoutCardView);
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

    ImageView selectedImageView;

    private void editCard(final BusinessCard businessCard) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.activity_create_new, null);

        final EditText titleField = subView.findViewById(R.id.editTextTitleBusinessCard);
        final EditText phoneField = subView.findViewById(R.id.editTextPhone);
        final EditText addressField = subView.findViewById(R.id.editTextTextAddress);
        final EditText descritpionField = subView.findViewById(R.id.editTextTextDescription);
        final ImageView imageField = subView.findViewById(R.id.selectedImage);

        final Button takePictureButton = subView.findViewById(R.id.takePicture);

        selectedImageView = imageField;

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity origin = (Activity)context;
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                origin.startActivityForResult(pickPhoto, 1);
            }
        });

        if (businessCard != null) {
            titleField.setText(businessCard.getTitle());
            phoneField.setText(String.valueOf(businessCard.getPhoneNumber()));
            addressField.setText(businessCard.getAddress());
            descritpionField.setText(businessCard.getDescription());
            imageField.setImageBitmap(DataConverter.convertByteArrayToBitmap(businessCard.getImage()));
        }


        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edycja");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String title = titleField.getText().toString();
                final String phone = phoneField.getText().toString();
                final String address = addressField.getText().toString();
                final String description = descritpionField.getText().toString();
                final byte[] image = DataConverter.convertImageViewToByteArray(imageField);

                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
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
                Toast.makeText(context, "Anulowano",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    public void saveLayout(FrameLayout frameLayout) {
        frameLayout.setDrawingCacheEnabled(true);
        Bitmap bitmap = frameLayout.getDrawingCache();
        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        Date now = new Date();
        String fileName = formatter.format(now);

        File file = new File(storageLoc, "wizytownik_" + fileName + ".jpg");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();

            scanFile(context, Uri.fromFile(file));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Toast.makeText(context, "Zapisano na urządzeniu", Toast.LENGTH_LONG).show();
    }

    private static void scanFile(Context context, Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);
    }

}
