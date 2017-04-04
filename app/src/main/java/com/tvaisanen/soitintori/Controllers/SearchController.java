package com.tvaisanen.soitintori.Controllers;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.tvaisanen.soitintori.Classes.Product;

import java.util.ArrayList;

/**
 * Created by tvaisanen on 8.1.2017.
 */

public class SearchController {

    private static SearchController instance = null;

    private SearchParameters params = new SearchParameters();
    private String keyword;
    private ToriRequest toriRequest;
    private EditText searchText;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayAdapter<Product> adapter;

    private SearchController(ArrayAdapter<Product> adapter){
        this.adapter = adapter;
    }

    public static SearchController getInstance(ArrayAdapter<Product> adapter){
        if (instance == null){
            instance = new SearchController(adapter);
        }
        return instance;
    }

    public void makeSearch(String keyword){
        boolean gettinRequest = toriRequest != null && !toriRequest.isResponseReady();
        if ( !gettinRequest ) {
            ArrayList<Product> productsFound;
            if (keyword == null) {
                keyword = "";
            }

            try {
                keyword = keyword + "&" + params.getSelectedType() +
                        "&" + params.getSelectedProvince();
            } catch (Exception ex) {
                Log.e("SearchController:", ex.toString());
            }
            System.out.println("Searching by: " + keyword);
            adapter.clear();
            toriRequest = new ToriRequest(keyword);
            productsFound = ToriRequest.getProducts();
            if (productsFound != null) {
                adapter.addAll(productsFound);
            }
        }
    }

    public ArrayList<Product> loadMoreProducts(){
        return null;

    }

    public boolean setType(String selected){
        params.setSelectedType(selected);
        if (params.getSelectedType() == selected){
            return true;
        } else return false;
    }

    public boolean setProvince(String selected){
        params.setSelectedProvince(selected);
        if (params.getSelectedProvince() == selected){
            return true;
        } else return false;
    }

    public boolean setCategory(String selected){
        params.setSelectedCategory(selected);
        if (params.getSelectedCategory() == selected){
            return true;
        } else return false;
    }

    public int getProductCount(){
        return products.size();
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
}
