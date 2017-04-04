package com.tvaisanen.soitintori.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tvaisanen.soitintori.Classes.Product;
import com.tvaisanen.soitintori.R;

/**
 * Created by tvaisanen on 16.12.2016.
 */


//  implements  View.OnClickListener
public class ProductViewActivity extends BaseActivity {

    Product product;

    AdView adView;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_activity);
        Toolbar toolbar = (Toolbar)findViewById(R.id.product_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent(); // gets the previously created intent
        product = (Product)myIntent.getSerializableExtra("product");

        actionBar.setTitle(product.getType() + " " + product.getTitle());

        adView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice("634837D103C53D25C626D5FC5D54FC5F").build();  //
        adView.loadAd(adRequest);


        LinearLayout lFooter = (LinearLayout) findViewById(R.id.productFooter);
        lFooter.setMinimumWidth((int)mScreenWidth);

        TextView tvType = (TextView) findViewById(R.id.tvType);
        TextView tvName = (TextView) findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        TextView tvLocation = (TextView) findViewById(R.id.tvLocation);
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);

        final ImageView ivThumbnail = (ImageView) findViewById(R.id.tvThumbnail);

        String id = product.getId();
        if (id == null){
            id = product.getUrl().split("/")[2];
        }

        try {
            System.out.println(product.getDescription());
        } catch (Exception ex){

        }

        try {

            tvType.setText(product.getType());
            tvName.setText(product.getTitle());
            tvDescription.setText(product.getDescription());
            tvLocation.setText(product.getLocation());
            tvPrice.setText(product.getPriceAsInt() + " €");


        } catch (Exception ex){

        }

        try {
            String url = "https://muusikoiden.net" + product.getImageUrl();
            ImageLoader.getInstance().displayImage(url, ivThumbnail);
        } catch (Exception ex){

        }

        ivThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setRotation(view.getRotation() + 90);
                ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }
}
