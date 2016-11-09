//package com.wowell.talboro2.view.navigation;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.wowell.talboro2.R;
//import com.wowell.talboro2.feature.MainActivity;
//import com.wowell.talboro2.view.TalAlertDialog;
//
///**
// * Created by kim on 2016-05-30.
// */
//public class NavigationLayout {
//    Context context;
//    Button navigationViewCloseBtn;
//    TextView supporterBtn;
//    NavigationUtil navigationUtil;
//
//    public NavigationLayout(MainActivity context, ViewGroup view, NavigationUtil navigationUtil) {
//        this.context = context;
//        this.navigationUtil = navigationUtil;
//        settingLayout(view);
//    }
//
//    private void settingLayout(ViewGroup view){
//
//        navigationViewCloseBtn = (Button) view.findViewById(R.id.main_navigation_view_close_btn);
//        navigationViewCloseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigationUtil.close();
//            }
//        });
//
//        supporterBtn = (TextView) view.findViewById(R.id.navigation_supporter_btn_text_view);
//        supporterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TalAlertDialog talAlertDialog = new TalAlertDialog(context, R.layout.layout_alert, 300, 300);
//                talAlertDialog.show();
//            }
//        });
//    }
//
//}
