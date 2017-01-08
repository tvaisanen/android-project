package com.tori.tv.mtori.Tasks;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


/**
 * Created by tvaisanen on 9.7.2016.
 */
public class HttpResponseTask extends AsyncTask<String, Void, Document> {

    String keyword;

    // Asynchronous task to retrieve html response of the given search

    public HttpResponseTask(String keyword_){
        keyword = keyword_;
    }

    private Document getResponse(String[] keyword) {
        // DEBUG
        System.out.println("DEBUG ........... action: getHttpResponse(searchTerm)");

        // Here the keyword[0] is temporary search parameter
        // TODO: complete url composition defined by search parameters

        String URL = "https://muusikoiden.net/tori/haku.php?keyword=" + keyword[0];
        try {
            Document documentFromUrl = Jsoup.connect(URL).userAgent("Android").timeout(0).get();
            return documentFromUrl;
        } catch(IOException ioe){
            System.out.println(ioe);
        }
        return null;
    }

    // This returns the received Document
    protected Document doInBackground(String... keyword){
        Document d = getResponse(keyword);
        System.out.println("action: doInBackground() { return getResponse(keyword) }");
        System.out.println("response document length: " + d.text().length());
        return d;
    }

}
