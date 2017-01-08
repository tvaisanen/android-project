package com.tori.tv.mtori.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tori.tv.mtori.Classes.Product;
import com.tori.tv.mtori.R;

import java.util.ArrayList;

/**
 * Created by tvaisanen on 9.7.2016.
 */
public class ProductsAdapter extends ArrayAdapter<Product> {

    private final Activity context;

    public ProductsAdapter(Activity context, ArrayList<Product> products) {
        super(context, 0, products);
        this.context = context;
    }

    static class ViewHolder {
        protected TextView text;
        protected ImageView image;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_view, parent, false);

        TextView tvType = (TextView) convertView.findViewById(R.id.tvType);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.tvThumbnail);

        tvType.setText(product.getType());
        tvName.setText(product.getTitle());
        tvDescription.setText(product.getDescription());
        tvLocation.setText(product.getLocation().split(" ")[0]);  // select just the city
        tvPrice.setText(product.getPriceAsInt() + " €");

        return convertView;
    }
}

