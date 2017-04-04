package com.tvaisanen.soitintori.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tvaisanen.soitintori.Classes.Product;
import com.tvaisanen.soitintori.Controllers.SearchController;
import com.tvaisanen.soitintori.R;

import java.util.ArrayList;

/**
 * Created by tvaisanen on 12.1.2017.
 */

public class ProductGridAdapter extends BaseAdapter {

    private final Activity context;
    private ArrayList<Product> products;

    public ProductGridAdapter(Activity context, ArrayList<Product> products) {
        super();
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = (Product) getItem(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.product_grid_layout, parent, false);

        TextView tvType = (TextView) convertView.findViewById(R.id.tvType);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.tvThumbnail);


        tvType.setText(product.getType());
        tvName.setText(product.getTitle());
        tvLocation.setText(product.getLocation().split(" ")[1]);  // select just the city
        if (product.getPriceAsInt() > 0) {
            tvPrice.setText(product.getPriceAsInt() + " €");
        }

        if (product.getImageUrl() == null) {
            // TODO: placeholder images
        } else {
            String url = "https://muusikoiden.net" + product.getImageUrl();
            ImageLoader.getInstance().displayImage(url, ivThumbnail);
        }
        return convertView;
    }
}
