package kdgs.kdgroup.config;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kdgs.kdgroup.BuildConfig;

/**
 * Created by Navneet Boghani on 7/3/16.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest {
    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {

        super(method, url, jsonRequest, listener, errorListener);
        Logger.debugE("url--> " + url + " jsonobject--> " + jsonRequest);
    }


    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        //if needed access toekn
        headers.put(Constants.HTTP_ATH, BuildConfig.APPLICATION_ID);


        Logger.debugE(Constants.HTTP_ATH, BuildConfig.APPLICATION_ID);
        return headers;
    }

}
