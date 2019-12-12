package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

    // CALLBACK
    public interface Listener { void onClickListItem(int position); }
    private final Listener callback;

    // FOR DATA
    private List<Property> mProperties;

    // CONSTRUCTOR
    public PropertyAdapter(Listener callback) {
        this.mProperties = new ArrayList<>();
        this.callback = callback;
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
        viewHolder.updateWithProperty(this.mProperties.get(position), this.callback);
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

}
