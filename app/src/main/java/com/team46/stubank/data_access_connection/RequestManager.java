package com.team46.stubank.data_access_connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * RequestManager class, singleton class for handling volley requests in android app
 *
 *
 * @author  George Cartridge
 * @version 1.0
 */
public class RequestManager {
    private static RequestManager instance;
    private RequestQueue requestQueue;
    private Context context;

    private RequestManager(Context context) {
        context = context;
        // create new request queue
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
