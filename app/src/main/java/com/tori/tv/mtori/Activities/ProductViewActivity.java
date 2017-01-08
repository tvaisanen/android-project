package com.tori.tv.mtori.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tori.tv.mtori.Classes.Product;
import com.tori.tv.mtori.R;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tvaisanen on 16.12.2016.
 */


//  implements  View.OnClickListener
public class ProductViewActivity extends BaseActivity {

    private static final Logger LOGGER = Logger.getLogger( ProductViewActivity.class.getName() );

    Product product;

    ProductViewActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);

        Intent myIntent = getIntent(); // gets the previously created intent
        Product product = (Product)myIntent.getSerializableExtra("product");
        System.out.println(product.toString());

        LinearLayout lFooter = (LinearLayout) findViewById(R.id.productFooter);

        lFooter.setMinimumWidth((int)mScreenWidth);

        TextView tvType = (TextView) findViewById(R.id.tvType);
        TextView tvName = (TextView) findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvLocation = (TextView) findViewById(R.id.tvLocation);
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        ImageView ivThumbnail = (ImageView) findViewById(R.id.tvThumbnail);

        String id = product.getId();
        if (id == null){
            id = product.getUrl().split("/")[2];
        }

        try {
            System.out.println(product.getDescription());
        } catch (Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }

        try {
            tvType.setText(product.getType());
            tvName.setText(product.getTitle());
            tvDescription.setText(product.getDescription());
            tvLocation.setText(product.getLocation());
            tvPrice.setText(product.getPriceAsInt() + " €");

        } catch (Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }

        try {
            String url = "https://muusikoiden.net" + product.getImageUrl();
            ImageLoader.getInstance().displayImage(url, ivThumbnail);
        } catch (Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }
}
