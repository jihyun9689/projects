package com.wowell.talboro2.utils.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;

import com.wowell.talboro2.utils.logger.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kim on 2016-08-21.
 */
public class ShareIntent {

    public static void sendIntent(Context context, String subject, String text){
        List<Intent> targetedShareIntents = new ArrayList<>();

        // Gmail
        Intent gmailIntent = getShareIntent(context, "gmail", subject, text);
        if(gmailIntent != null)
            targetedShareIntents.add(gmailIntent);

        // 구글 플러스
        Intent googlePlusIntent = getShareIntent(context, "com.google.android.apps.plus", subject, text);
        if(googlePlusIntent != null)
            targetedShareIntents.add(googlePlusIntent);

        // 페이스북
        Intent facebookIntent = getShareIntent(context, "facebook", subject, text);
        if(facebookIntent != null)
            targetedShareIntents.add(facebookIntent);

        // 트위터
        Intent twitterIntent = getShareIntent(context, "twitter", subject, text);
        if(twitterIntent != null)
            targetedShareIntents.add(twitterIntent);

        // kakao
        Intent kakaoIntent2 = getShareIntent(context, "memochatconnectactivity", subject, text);
        if(kakaoIntent2 != null)
            targetedShareIntents.add(kakaoIntent2);

        //카카오톡의 경우 2개의 intent를 가진다.(나에게, 그냥 카카오)
        //remove(0)일 경우 하나씩만, remove(targetedShareIntents.size() -1)경우 모두를 표시한다.
        Intent chooser = Intent.createChooser(targetedShareIntents.remove(targetedShareIntents.size() - 1), "공유");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
        context.startActivity(chooser);
    }

    private static Intent getShareIntent(Context context, String name, String subject, String text) {
        boolean found = false;

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

        if(resInfo == null)
            return null;

        for (ResolveInfo info : resInfo) {
            LogManager.printLog(ShareIntent.class, "resInfo : " + info.activityInfo.name);
            if(info.activityInfo.packageName.toLowerCase().contains(name) ||
                    info.activityInfo.name.toLowerCase().contains(name) ) {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setPackage(info.activityInfo.packageName);
                found = true;
                break;
            }
        }

        if (found)
            return intent;

        return null;
    }
}
