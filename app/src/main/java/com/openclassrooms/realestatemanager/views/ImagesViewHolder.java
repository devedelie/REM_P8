package com.openclassrooms.realestatemanager.views;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Eliran Elbaz on 22-Dec-19.
 */
public class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.property_image_in_list) ImageView propertyImage;
    @BindView(R.id.property_description_in_image_list) TextView propertyImageDescription;
    private ImagesAdapter.OnPhotoClick mOnPhotoClick;

    public ImagesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        propertyImage.setOnClickListener(this);
    }

    public void updatePropertyImages(String imageUri, String imageDescription, RequestManager glide,
                                     ImagesAdapter.OnPhotoClick callback_onPhotoClick){
        if(propertyImageDescription !=null) propertyImageDescription.setText(imageDescription);
        if(imageUri != null) glide.load(imageUri).into(propertyImage);
        mOnPhotoClick = callback_onPhotoClick;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: onPhotoClick");
        mOnPhotoClick.onPhotoClick(getAdapterPosition() ,v);
    }
}
