package com.tvaisanen.soitintori.Activities;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.tvaisanen.soitintori.Adapters.ExpandableListAdapter;
import com.tvaisanen.soitintori.Classes.Product;

import com.tvaisanen.soitintori.Controllers.SearchParameters;
import com.tvaisanen.soitintori.Controllers.ToriRequest;

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



        MainActivity.context = getApplicationContext();
        context = getApplicationContext();


        // Trying to hack together drawable nav for search


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mActivityTitle = getTitle().toString();
        offset = 1;
        pageNumber = 1;

        // -------------------------------------------- //

        // Setup Search Button




        // --------------------------------------------//

        searchParameters = new SearchParameters();
        products = new ArrayList<>(10);



        // Populate products with latest products

        toriRequest = new ToriRequest(keyword);
        products = ToriRequest.getProducts();
        productResultCount = toriRequest.getProductCount();
        productsAdapter.clear();
        for (Product p : products) {
            productsAdapter.add(p);
        }

        updateCountTitle(true);













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
