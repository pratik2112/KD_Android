package kdgs.kdgroup.config;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import kdgs.kdgroup.R;
import kdgs.kdgroup.utills.AppController;

/**
 * Created by Navneet Boghani on 01,June,2016
 */
public class WebService {

    String serviceUrl = "";
    JSONObject jsonObject;
    boolean ishowPrograssBar = true;
    Context context;

    OnResult onResult;

    public WebService(String serviceUrl, JSONObject jsonObject, boolean ishowPrograssBar, Context context) {
        this.serviceUrl = serviceUrl;
        this.jsonObject = jsonObject;
        this.ishowPrograssBar = ishowPrograssBar;
        this.context = context;


    }

    public interface OnResult {
        public void OnSuccess(JSONObject result);

        public void OnFail(String error);
    }

    public WebService(String serviceUrl, JSONObject jsonObject, Context context) {
        this.serviceUrl = serviceUrl;
        this.jsonObject = jsonObject;
        this.context = context;

    }

    public void getData(int method, OnResult onResult) {
        try {
            this.onResult = onResult;
            if (ishowPrograssBar)
                CommonFunctions.createProgressBar(context, context.getString(R.string.msg_please_wait));
            CustomJsonObjectRequest jsonObjReq = new CustomJsonObjectRequest(method, serviceUrl, jsonObject, RSJsonObjectListener, REsErrorListener);
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Logger.debugE("jsonObjReq url--->", serviceUrl.toString() + " jsondata\n " + jsonObject.toString());
            if (AppController.getInstance() != null)
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            else
                onResult.OnFail(context.getString(R.string.msg_server_error));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData(OnResult onResult) {
        try {
            this.onResult = onResult;
            if (ishowPrograssBar)
                CommonFunctions.createProgressBar(context, context.getString(R.string.msg_please_wait));
            CustomJsonObjectRequest jsonObjReq = new CustomJsonObjectRequest(Request.Method.POST, serviceUrl, jsonObject, RSJsonObjectListener, REsErrorListener);
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Logger.debugE("jsonObjReq url--->", serviceUrl.toString() + " jsondata\n " + jsonObject);
            if (AppController.getInstance() != null)
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            else
                onResult.OnFail(context.getString(R.string.msg_server_error));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Response.Listener<JSONObject> RSJsonObjectListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                if (ishowPrograssBar)
                    CommonFunctions.destroyProgressBar();
                Logger.debugE("RSJsonObjectListener Response--->", response.toString());
                onResult.OnSuccess(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    Response.ErrorListener REsErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                if (ishowPrograssBar)
                    CommonFunctions.destroyProgressBar();
                VolleyLog.d("", "Error: " + error.getMessage());
                onResult.OnFail(context.getString(R.string.msg_server_error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
