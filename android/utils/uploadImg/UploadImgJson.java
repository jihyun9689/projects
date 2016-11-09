package com.wowell.talboro2.utils.uploadImg;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wowell.talboro2.utils.logger.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by kim on 2016-05-25.
 */
public class UploadImgJson {
    public static void uploadImage(final Context context,  String uploadURL, final String fileName, final Bitmap bitmap){
        //Showing the progress dialog
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("filename", fileName);
            jsonObject.put("image", getStringImage(bitmap));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog loading = ProgressDialog.show(context, "Uploading...", "Please wait...", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                uploadURL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogManager.printLog(getClass(), "response : " + response.toString());
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogManager.printError(getClass(), "error : " + error.getMessage());
                        loading.dismiss();
                    }
                }
        ){

//            @Override
//            public byte[] getBody() {
//                return getBytesImage(bitmap);
//            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);

    }


    private static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
