package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eliran Elbaz on 22-Dec-19.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {
    // FOR DATA
    private final List<String> mPhotos = new ArrayList<>();
    private final List<String> mPhotosDescription = new ArrayList<>();
    // Declaring a Glide object
    private RequestManager mGlide;

    public interface OnPhotoClick{
        void onPhotoClick(int position,View view);
    }
    private final OnPhotoClick mCallback_OnPhotoClick;

    // CONSTRUCTOR
    public ImagesAdapter( RequestManager glide, OnPhotoClick callback_OnPhotoClick) {
        mGlide = glide;
        mCallback_OnPhotoClick = callback_OnPhotoClick;
    }

    public void setPropertyImagesList(List<String> imagesUriList){
        this.mPhotos.clear();
        this.mPhotos.addAll(imagesUriList);
        notifyDataSetChanged();
    }

    public void setPropertyDescriptionList( List<String> photoDescription){
        this.mPhotosDescription.clear();
        this.mPhotosDescription.addAll(photoDescription);
        notifyDataSetChanged();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_list_item, parent, false);

        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        holder.updatePropertyImages(mPhotos.get(position), mPhotosDescription.get(position), mGlide, mCallback_OnPhotoClick);
    }

    @Override
    public int getItemCount() {
        return this.mPhotos.size();
    }
}
