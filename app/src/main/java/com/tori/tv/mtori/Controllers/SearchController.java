package com.tori.tv.mtori.Controllers;

import android.util.Log;
import android.widget.EditText;

import com.tori.tv.mtori.Classes.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by tvaisanen on 8.1.2017.
 */

public class SearchController {

    private SearchParameters params;
    private String keyword;
    private ToriRequest toriRequest;
    private EditText searchText;
    private ArrayList<Product> products;

    public SearchController(){
        params = new SearchParameters();
    }

    public ArrayList<Product> makeSearch(String keyword){

        if (keyword == null){
            keyword = "";
        }

        try {
            keyword = keyword + "&" + params.getSelectedType() +
                    "&" + params.getSelectedProvince();
        } catch (Exception ex){
            Log.e("SearchController:",ex.toString());
        }
        System.out.println("Searching by: " + keyword);
        toriRequest = new ToriRequest(keyword);
        return ToriRequest.getProducts();
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
}
