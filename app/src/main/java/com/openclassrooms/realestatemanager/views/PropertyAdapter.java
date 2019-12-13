package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {


    // FOR DATA
    private List<Property> mProperties;
    // Declaring a Glide object
    private RequestManager mGlide;
    // CALLBACK
    private final OnPropertyClick mCallback;

    // CONSTRUCTOR
    public PropertyAdapter(List<Property> properties, RequestManager glide, OnPropertyClick callback) {
        this.mProperties = properties;
        this.mGlide = glide;
        this.mCallback = callback;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.property_list_item, parent, false);

        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder viewHolder, int position) {
        viewHolder.updateWithProperty(mProperties.get(position), mGlide, mCallback);
    }

    @Override
    public int getItemCount() {
        return this.mProperties.size();
    }

    public Property getProperty(int position){
        return this.mProperties.get(position);
    }

    public void updateData(List<Property> properties){
        this.mProperties = properties;
        this.notifyDataSetChanged();
    }

    public interface OnPropertyClick{
        void onPropertyClick(Property property);
    }

}
