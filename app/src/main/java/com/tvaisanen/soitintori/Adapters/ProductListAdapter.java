package com.tvaisanen.soitintori.Adapters;

/**
 * Created by tvaisanen on 10.8.2016.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tvaisanen.soitintori.Classes.Product;
import com.tvaisanen.soitintori.R;

import java.util.ArrayList;




/**
 * Created by tvaisanen on 9.7.2016.
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

    private final Activity context;
    private static ProductListAdapter instance;

    private ProductListAdapter(Activity context, ArrayList<Product> products) {
        super(context, 0, products);
        this.context = context;
    }

    public static ProductListAdapter getInstance(Activity context, ArrayList<Product> products){
        if (instance == null){
            instance = new ProductListAdapter(context, products);
        }
        return instance;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);

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
        Bitmap productIcon = null;

        if (product.getImageUrl() == null){
            // TODO: placeholder images
        } else {
            String url = "https://muusikoiden.net"+ product.getImageUrl();
            ImageLoader.getInstance().displayImage(url, ivThumbnail);
        }


        // When using this the click listener stops working!

        /*
        final int DEFAULT_THRESHOLD = 128;

        convertView.setOnTouchListener(new View.OnTouchListener() {

            int initialX = 0;
            final float slop = ViewConfiguration.get(context).getScaledTouchSlop();

            public boolean onTouch(final View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    initialX = (int) event.getX();
                    view.setPadding(0, 0, 0, 0);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int currentX = (int) event.getX();
                    int offset = currentX - initialX;
                    if (Math.abs(offset) > slop) {
                        view.setPadding(offset, 0, 0, 0);

                        if (offset > DEFAULT_THRESHOLD) {
                            // TODO :: Do Right to Left action! And do nothing on action_up.
                        } else if (offset < -DEFAULT_THRESHOLD) {
                            // TODO :: Do Left to Right action! And do nothing on action_up.
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    // Animate back if no action was performed.
                    ValueAnimator animator = ValueAnimator.ofInt(view.getPaddingLeft(), 0);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            view.setPadding((Integer) valueAnimator.getAnimatedValue(), 0, 0, 0);
                        }
                    });
                    animator.setDuration(150);
                    animator.start();
                }
                return true;
            };
        });
       */

        return convertView;
    }
}
