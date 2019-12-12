package com.openclassrooms.realestatemanager.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    @BindView(R.id.item) ConstraintLayout mConstraintLayout;

    // FOR DATA
    private WeakReference<PropertyAdapter.Listener> callbackWeakRef;

    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithProperty(Property property, PropertyAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<PropertyAdapter.Listener>(callback);
        // Set callback for when clicking on a list frame
        this.mConstraintLayout.setOnClickListener(this);

        propertyTypeText.setText(property.getType());
        propertyLocationText.setText(property.getLocation());
        propertyPriceText.setText(String.valueOf(Utils.moneyValueFormatter(property.getPropertyPrice())));

    }

    @Override
    public void onClick(View v) {
        PropertyAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickListItem(getAdapterPosition());
    }
}
