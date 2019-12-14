package com.openclassrooms.realestatemanager.views;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by Eliran Elbaz on 24-Nov-19.
 */
public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.item_type_text) TextView propertyTypeText;
    @BindView(R.id.item_location_text) TextView propertyLocationText;
    @BindView(R.id.item_price_text) TextView propertyPriceText;
    @BindView(R.id.item_property_image) ImageView propertyImage;
    @BindView(R.id.item_sold_image) ImageView propertySoldImage;
//    @BindView(R.id.item) ConstraintLayout mConstraintLayout;

    private Property mProperty;
    private PropertyAdapter.OnPropertyClick mOnPropertyClick;



    public PropertyViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void updateWithProperty(Property property, RequestManager glide, PropertyAdapter.OnPropertyClick callback){
        this.mOnPropertyClick = callback;
        this.mProperty = property;
        propertyImage.setImageResource(R.drawable.apart_one); // Testing
        propertyTypeText.setText(property.getType());
        propertyLocationText.setText(property.getLocation());
        propertyPriceText.setText(String.valueOf(Utils.moneyValueFormatter(property.getPropertyPrice())));

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        if (mOnPropertyClick != null) mOnPropertyClick.onPropertyClick(mProperty);
    }
}
