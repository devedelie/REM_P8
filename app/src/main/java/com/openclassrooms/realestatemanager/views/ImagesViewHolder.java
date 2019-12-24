package com.openclassrooms.realestatemanager.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eliran Elbaz on 22-Dec-19.
 */
public class ImagesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.property_image_in_list) ImageView propertyImage;
    @BindView(R.id.property_description_in_image_list) TextView propertyImageDescription;

    private Property mProperty;


    public ImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void updatePropertyImages(String imageUri, RequestManager glide){
        propertyImageDescription.setText("Living room");
        if(imageUri != null) glide.load(imageUri).into(propertyImage);
    }
}
