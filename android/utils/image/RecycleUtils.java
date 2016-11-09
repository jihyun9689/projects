package com.wowell.talboro2.utils.image;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.wowell.talboro2.utils.logger.LogManager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by kim on 2016-06-30.
 */
public class RecycleUtils {

    public static void recursiveRecycle(View root) {
        if (root == null) return;

        int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackgroundDrawable(null);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                root.setBackground(null);
            }
        }

        //SwipeRefreshLayout을 사용할 경우 바로 removeAllViews할 경우 에러 발생
        if(root instanceof SwipeRefreshLayout){
            ((SwipeRefreshLayout) root).setRefreshing(false);
            ((SwipeRefreshLayout) root).destroyDrawingCache();
            ((SwipeRefreshLayout) root).clearAnimation();
            ((SwipeRefreshLayout) root).removeAllViews();
        }

        if (root instanceof ViewGroup) {

            ViewGroup group = (ViewGroup) root;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                recursiveRecycle(group.getChildAt(i));
            }
            if (!(root instanceof AdapterView)) {
                group.removeAllViews();
            }
        }
        if (root instanceof ImageView) {
            ((ImageView) root).setImageDrawable(null);
        }
        root = null;
        return;
    }

    public static void recursiveRecycle(List<WeakReference<View>> recycleList) {
        for (WeakReference<View> ref : recycleList)
            recursiveRecycle(ref.get());
    }
}
