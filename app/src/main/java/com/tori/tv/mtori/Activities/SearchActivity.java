package com.tori.tv.mtori.Activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tori.tv.mtori.Classes.Product;
import com.tori.tv.mtori.Controllers.SearchController;
import com.tori.tv.mtori.Controllers.ToriRequest;
import com.tori.tv.mtori.R;
import com.tori.tv.mtori.Utils.ListUtils;

import java.util.ArrayList;

/**
 * Created by tvaisanen on 8.1.2017.
 */

public class SearchActivity extends BrowseActivity {

    // TODO: Try to embed these in to the titlebar
    ToriRequest toriRequest;
    EditText searchText;
    SearchController searchController;
    Button searchButton;
    String keyword;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initSideMenuData();
        setSideMenuListeners();
        searchController = new SearchController();
        setSearchButton();
        setSearchTextListener();
        products = search();
        populateProductList(products);
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
                    products = search();
                    populateProductList(products);
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

    public void populateProductList(ArrayList<Product> products){

        for( Product p : products) {
            productsAdapter.add(p);
        }
    }

    private ArrayList<Product> search(){
        try {
            keyword = searchText.getText().toString();
            hideKeyboard(getCurrentFocus());
        } catch (Exception e){
            keyword = "";
        }
        productsAdapter.clear();
        return searchController.makeSearch(keyword);
    }

    private void setSearchButton(){
        searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View parent) {
                products = search();
                populateProductList(products);
            }
        });
    }

    protected void listEndAction(){
        System.out.println("Overriding listEndAction");
    }

}
