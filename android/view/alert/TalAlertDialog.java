package com.wowell.talboro2.view.alert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

/**
 * Created by kim on 2016-05-27.
 */
public class TalAlertDialog {
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    float widthPX = 0, heightPX = 0;
    View layout;
    Context context;

    public TalAlertDialog(Context context, int layoutResource) {
        this.context = context;
        LayoutInflater inflater2 = LayoutInflater.from(context);
        layout = inflater2.inflate(layoutResource, null);

        builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();

        //모서리는 둥굴게 할 경우 모서리 부분에 하얀색이 남는 현상 제거를 위해서
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public TalAlertDialog(Context context, int layoutResource, int widthDP, int heightDP) {

        LayoutInflater inflater2 = LayoutInflater.from(context);
        layout = inflater2.inflate(layoutResource, null);

        builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();
        //모서리는 둥굴게 할 경우 모서리 부분에 하얀색이 남는 현상 제거를 위해서
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        widthPX = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, widthDP, context.getApplicationContext().getResources().getDisplayMetrics()
        );
        heightPX = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, heightDP, context.getApplicationContext().getResources().getDisplayMetrics()
        );
    }

    public View getLayout(){
        return layout;
    }

    public void show(){
        alertDialog.show();

        //dialog 사이즈 조절절
        if(widthPX != 0 && heightPX != 0){
            LayoutParams params = alertDialog.getWindow().getAttributes();
            params.width = (int)widthPX;
            params.height = (int)heightPX;
            alertDialog.getWindow().setAttributes(params);
        }
    }

    public void dismiss(){
        alertDialog.dismiss();
    }

    public void replaceView(int layoutResource){
        LayoutInflater inflater2 = LayoutInflater.from(context);
        View view = inflater2.inflate(layoutResource, null);
        alertDialog.setContentView(view);
    }
}
