package com.tori.tv.mtori.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tori.tv.mtori.Adapters.ExpandableListAdapter;
import com.tori.tv.mtori.Classes.Product;
import com.tori.tv.mtori.Adapters.ProductListAdapter;
import com.tori.tv.mtori.R;
import com.tori.tv.mtori.Controllers.SearchParameters;
import com.tori.tv.mtori.Controllers.ToriRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MainActivity extends BaseActivity {

    private static final Logger LOGGER = Logger.getLogger( ProductViewActivity.class.getName() );

    // Display Metrics


    //  Variables for drawable search properties menu

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ExpandableListView mExpDrawerList;
    private ExpandableListAdapter mExpListAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private TextView tvProductCount;

    private int lastExpandedPosition = -1;

    // -------------------------------------------- //

    // variables for products listview

    ArrayList<Product> products;
    ArrayAdapter<Product> productsAdapter;

    private int productsLeft;
    private int productResultCount;
    private String keyword;
    private String offset_keyword;
    private int offset;
    private int pageNumber;


    // ---------------------------------------------- //

    ToriRequest toriRequest;

    EditText searchTerm;

    SearchParameters searchParameters;

    Button searchButton;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MainActivity.context = getApplicationContext();
        context = getApplicationContext();


        // Trying to hack together drawable nav for search

        mExpDrawerList = (ExpandableListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        tvProductCount = (TextView)findViewById(R.id.productCount);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mActivityTitle = getTitle().toString();
        offset = 1;
        pageNumber = 1;

        // -------------------------------------------- //

        // Setup Search Button

        searchTerm = (EditText) findViewById(R.id.searchKeyword);
        searchButton = (Button) findViewById(R.id.search);


        // --------------------------------------------//

        searchParameters = new SearchParameters();
        products = new ArrayList<>(10);
        productsAdapter = new ProductListAdapter(this, products);


        // Populate products with latest products

        toriRequest = new ToriRequest(keyword);
        products = ToriRequest.getProducts();
        productResultCount = toriRequest.getProductCount();
        productsAdapter.clear();
        for (Product p : products) {
            productsAdapter.add(p);
        }

        updateCountTitle(true);




        final ListView productList = (ListView) findViewById(R.id.productList);

        productList.setAdapter(productsAdapter);








    }






    // -------------------------------------------- //





    private void makeSearch(){
        hideKeyboard(searchTerm); //  Hide keyboard everytime when search gets made
        keyword = searchTerm.getText().toString();
        try {
            keyword = keyword + "&" + searchParameters.getSelectedType() +
                    "&" + searchParameters.getSelectedProvince();
        } catch (Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex);
        }
        System.out.println("Searching by: " + keyword);
        toriRequest = new ToriRequest(keyword);
        products = ToriRequest.getProducts();
        productResultCount = toriRequest.getProductCount();
        productsAdapter.clear();
        for (Product p : products) {
            productsAdapter.add(p);
        }

        updateCountTitle(true);
    }




    private void updateCountTitle(boolean productsLeftToLoad){
        if (productsLeftToLoad) {
            tvProductCount.setText("Ladattu " + productsAdapter.getCount() + "/" + productResultCount);
        } else {
            tvProductCount.setText("Ladattu kaikki " + productsAdapter.getCount() + "/" + productResultCount);
        }
    }

}
