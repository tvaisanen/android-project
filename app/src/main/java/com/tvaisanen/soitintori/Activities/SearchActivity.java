package com.tvaisanen.soitintori.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.tvaisanen.soitintori.Classes.Product;
import com.tvaisanen.soitintori.Controllers.SearchController;
import com.tvaisanen.soitintori.R;
import com.tvaisanen.soitintori.Utils.ListUtils;

import java.util.ArrayList;

/**
 * Created by tvaisanen on 8.1.2017.
 */

public class SearchActivity extends BrowseActivity {

    // TODO: Try to embed these in to the titlebar
    EditText searchText;
    SearchController searchController;
    AdView adView;
    AdRequest adRequest;


    Button searchButton;
    String keyword;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);

        MobileAds.initialize(getApplicationContext(), getString(R.string.admobs_app_id));

        setSupportActionBar(toolbar);
        searchController = SearchController.getInstance(productArrayAdapter);
        initSideMenuData();
        setSideMenuListeners();
        //setSearchButton();
        //setSearchTextListener();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (searchController.getProducts() == null) {
            searchController.makeSearch(getKeyword());
        }
        adView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().addTestDevice("634837D103C53D25C626D5FC5D54FC5F").build();  //
        adView.loadAd(adRequest);

    }

    @Override
    protected void onResume(){
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    protected void populateProductList() {
    }

    protected void setSearchTextListener(){

        searchText = (EditText) findViewById(R.id.searchKeyword);

        // make SearchButton to respond "ENTER"
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == KeyEvent.KEYCODE_ENTER){
                    searchController.makeSearch(getKeyword());

                    return true;
                }
                return false;
            }
        });

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    protected void initSideMenuData() {
        /*
            Initialize data for side menu, and add menu items afterwards
         */
        ListUtils.setSearchSideMenuData(listDataHeader, listDataChild);
        addMenuItems();
    }

    protected void setSideMenuListeners() {
        // Listview on child click listener
        mExpDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String selected = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                String section = listDataHeader.get(groupPosition);

                Toast.makeText(
                        getApplicationContext(), section + " : " + selected, Toast.LENGTH_SHORT).show();

                if (section == "Ilmoitustyyppi") {
                    searchController.setType(selected);
                } else if (section == "Sijainti") {
                    searchController.setProvince(selected);
                } else if (section == "Osasto") {
                    searchController.setCategory(selected);
                }
                return false;
            }
        });
    }


    private String getKeyword(){
        hideKeyboard(getCurrentFocus());
        try {
            return searchText.getText().toString();
        } catch (Exception e){
        }
        return keyword = "";
    }

    private void setSearchButton(){
        searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View parent) {
                searchController.makeSearch(getKeyword());
            }
        });
    }

    protected void listEndAction(){
        // TODO: Get more products!
        System.out.println("Overriding listEndAction");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                searchView.clearFocus();
                searchController.makeSearch(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

}
