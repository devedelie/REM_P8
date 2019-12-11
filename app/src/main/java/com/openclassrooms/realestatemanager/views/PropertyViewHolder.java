package com.openclassrooms.realestatemanager.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.item_type_text) TextView propertyTypeText;
    @BindView(R.id.item_location_text) TextView propertyLocationText;
    @BindView(R.id.item_price_text) TextView propertyPriceText;
    @BindView(R.id.item_property_image) ImageView propertyImage;
    @BindView(R.id.item_sold_image) ImageView propertySoldImage;

    // FOR DATA
    private WeakReference<PropertyAdapter.Listener> callbackWeakRef;

    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithProperty(Property property, PropertyAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<PropertyAdapter.Listener>(callback);
        propertyTypeText.setText(property.getAgentInCharge());
        propertyLocationText.setText("hjhjhj");
        propertyPriceText.setText("2,000,000");

    }


    @Override
    public void onClick(View v) {
        PropertyAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
