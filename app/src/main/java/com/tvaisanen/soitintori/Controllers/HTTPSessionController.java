package com.tvaisanen.soitintori.Controllers;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by tvaisanen on 8.1.2017.
 */



public class HTTPSessionController {

    private static HTTPSessionController instance = null;

    private HttpClient httpClient = new DefaultHttpClient();
    private CookieStore cookieStore = new BasicCookieStore();


    protected HTTPSessionController(){

    }

    public static HTTPSessionController getInstance(){
        if (instance == null) {
            instance = new HTTPSessionController();
        }
        return instance;
    }
}
