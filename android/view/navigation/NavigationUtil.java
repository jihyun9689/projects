//package com.wowell.talboro2.view.navigation;
//
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//import com.wowell.talboro2.feature.MainActivity;
//
///**
// * Created by kim on 2016-05-20.
// */
//public class NavigationUtil {
//    FrameLayout mainFrameLayout, navigationLinearLayout;
//    LinearLayout hintLinearLayout , hiddenLayout;
//    MainActivity context;
//    static final int widthDP = 300;
//    float widthPX;
//    boolean touch_flag = false;
//    float upX = 0;
//
//    public NavigationUtil(MainActivity mainActivity, ViewGroup navigationView, ViewGroup MainLayout , ViewGroup hintLauout) {
//        context = mainActivity;
//        navigationLinearLayout = (FrameLayout)navigationView;
//        mainFrameLayout = (FrameLayout)MainLayout;
//        hintLinearLayout = (LinearLayout)hintLauout;
//        //hiddenLayout = (LinearLayout) mainActivity.findViewById(R.id.navigation_hidden_view);
//        widthPX = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, widthDP, context.getApplicationContext().getResources().getDisplayMetrics()
//        );
//        init();
//    }
//
//    private void init(){
//        new NavigationLayout(context, navigationLinearLayout, this);
//
//        //힌트 레이아웃 뒤에 있는 리스너 방지를 위해서 구현
////        hintLinearLayout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
////        hiddenLayout.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                if(event.getAction() == MotionEvent.ACTION_DOWN){
////                    LogManager.printLog(getClass(),"ACTION_DOWN");
////                    upX = event.getRawX();
////                    return true;
////                }
////                if(event.getAction() == MotionEvent.ACTION_MOVE){
////                    LogManager.printLog(getClass(),"ACTION_MOVE");
////                    if(upX > event.getRawX() + 60){
////                        close();
////                        return true;
////                    }
////
////                }
////                if(event.getAction() == MotionEvent.ACTION_UP){
////                    LogManager.printLog(getClass(),"ACTION_UP");
////                    return false;
////                }
////                return false;
////            }
////        });
//    }
//
//    public void open(){
//        mainFrameLayout.setVisibility(View.VISIBLE);
//        TranslateAnimation translateAnimation  = new TranslateAnimation(
//                Animation.ABSOLUTE, 0,
//                Animation.ABSOLUTE, widthPX,
//                Animation.ABSOLUTE, 0,
//                Animation.ABSOLUTE, 0);
//        translateAnimation.setDuration(500);
//
//        hintLinearLayout.setVisibility(View.VISIBLE);
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.5f);
//        alphaAnimation.setDuration(500);
//        alphaAnimation.setFillAfter(true);
//        hintLinearLayout.setAnimation(alphaAnimation);
//
//        mainFrameLayout.startAnimation(translateAnimation);
//        hintLinearLayout.startAnimation(alphaAnimation);
//
//
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//            int left, top, right, bottom;
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //애니메이션 효과 이후 클릭리스너가 이동하지 않는 것을 해결하기 위해서
//                mainFrameLayout.clearAnimation();
//                left = mainFrameLayout.getLeft();
//                top = mainFrameLayout.getTop();
//                right = mainFrameLayout.getRight();
//                bottom = mainFrameLayout.getBottom();
//                mainFrameLayout.layout(left + (int)widthPX, top, right + (int)widthPX, bottom);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
//
//    public void close(){
//        mainFrameLayout.setVisibility(View.VISIBLE);
//        TranslateAnimation translateAnimation  = new TranslateAnimation(
//                Animation.ABSOLUTE, widthPX,
//                Animation.ABSOLUTE, 0,
//                Animation.ABSOLUTE, 0,
//                Animation.ABSOLUTE, 0);
//        translateAnimation.setDuration(500);
//
//        hintLinearLayout.setVisibility(View.VISIBLE);
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0);
//        alphaAnimation.setDuration(500);
//        hintLinearLayout.setAnimation(alphaAnimation);
//        mainFrameLayout.startAnimation(translateAnimation);
//        hintLinearLayout.startAnimation(alphaAnimation);
//        hintLinearLayout.setVisibility(View.GONE);
//
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//            int left, top, right, bottom;
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mainFrameLayout.clearAnimation();
//                left = mainFrameLayout.getLeft();
//                top = mainFrameLayout.getTop();
//                right = mainFrameLayout.getRight();
//                bottom = mainFrameLayout.getBottom();
//                mainFrameLayout.layout(left, top, right, bottom);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
//}
