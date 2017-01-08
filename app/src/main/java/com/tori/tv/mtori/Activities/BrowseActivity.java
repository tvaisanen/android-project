package com.tori.tv.mtori.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.tori.tv.mtori.Adapters.ExpandableListAdapter;
import com.tori.tv.mtori.Classes.Product;
import com.tori.tv.mtori.Adapters.ProductListAdapter;
import com.tori.tv.mtori.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by tvaisanen on 8.1.2017.
 */


public abstract class BrowseActivity extends BaseActivity {

    // Variables for drawable search properties menu
    // Must be populated in sub class

    protected List<String> listDataHeader;
    protected HashMap<String, List<String>> listDataChild;
    protected ListView mDrawerList;
    protected ArrayAdapter<String> mAdapter;
    protected ExpandableListView mExpDrawerList;
    protected ExpandableListAdapter mExpListAdapter;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected DrawerLayout mDrawerLayout;
    protected String mActivityTitle;
    protected TextView tvProductCount;

    protected int lastExpandedPosition = -1;

    // -------------------------------------------- //

    // Variables for products ListView

    protected ArrayList<Product> products;
    protected ArrayAdapter<Product> productsAdapter;
    protected ListView productList;

    private Runnable listEndRunnable = new Runnable(){
        public void run(){
            listEndAction();
        }
    };

    // ---------------------------------------------- //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExpDrawerList = (ExpandableListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        products = new ArrayList<>(10);
        productsAdapter = new ProductListAdapter(this, products);
        productList = (ListView) findViewById(R.id.productList);
        productList.setAdapter(productsAdapter);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        setSideMenu();
        setListListeners(listEndRunnable);

    }


    private void setSideMenu(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        System.out.println("mDrawerToggle:\n" + mDrawerToggle.toString());
        //mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    protected void addMenuItems() {
        // This gets called by subclass after intializing listDataHeader nad listDataChild
        mExpListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        mExpDrawerList.setAdapter(mExpListAdapter);

        // Listview Group click listener
        mExpDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        mExpDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                // this collapses other groups than selected
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition){
                    mExpDrawerList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        // Listview Group collapsed listener
        mExpDrawerList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });
    }

    protected void setListListeners(final Runnable listEndAction){

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // By default opens a new product view
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int i, long id) {
                Intent productViewIntent = new Intent(getApplicationContext(),
                                                        ProductViewActivity.class);

                Product productToView = (Product)parent.getItemAtPosition(i);
                // send clicked product info to ProductView
                productViewIntent.putExtra("product", productToView);
                startActivity(productViewIntent);
            }
        });

        productList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (productList.getLastVisiblePosition() - productList.getHeaderViewsCount() -
                        productList.getFooterViewsCount()) >= (productsAdapter.getCount() - 1)) {

                    System.out.println("----------- LIST BOTTOM --------------");

                    listEndAction.run(); // is it working?

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });

    };


    protected abstract void populateProductList();
    protected abstract void initSideMenuData();
    protected abstract void setSideMenuListeners();
    protected abstract void listEndAction(); // -> runnable -> exec end of the list.
}

