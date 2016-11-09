package com.wowell.talboro2.view.alert;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wowell.talboro2.R;
import com.wowell.talboro2.base.state.RelationState;
import com.wowell.talboro2.controller.ResponseController;
import com.wowell.talboro2.controller.request.RequestController;
import com.wowell.talboro2.controller.request.RequestInterface;
import com.wowell.talboro2.controller.request.RequestMethod;
import com.wowell.talboro2.controller.request.ResponseListner;
import com.wowell.talboro2.controller.request.url.RequestRelation;
import com.wowell.talboro2.model.main.RelationRequestModel;
import com.wowell.talboro2.utils.logger.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kim on 2016-07-14.
 */
public class AlertView {
    View confirmView, cancelView, editTextView;
    TalAlertDialog talAlertDialog;
    AlertListener alertListener;
    AlertEnum alertEnum;
    Context context;

    AlertView newAlertView;

    public AlertView(Context context, AlertEnum alertEnum, final AlertListener alertListener) {
        this.alertListener = alertListener;
        this.alertEnum = alertEnum;
        this.context = context;

        if(alertEnum == AlertEnum.FIND_ID){
            talAlertDialog = new TalAlertDialog(context, R.layout.layout_alert_find_nickname, 300, 300);
            confirmView = talAlertDialog.getLayout().findViewById(R.id.alert_find_nickname_search_btn);
            cancelView = talAlertDialog.getLayout().findViewById(R.id.alert_find_nickname_cancel_btn);
            editTextView = talAlertDialog.getLayout().findViewById(R.id.alert_find_nickname_edit_text);
        }else if(alertEnum == AlertEnum.RESULT){
            talAlertDialog = new TalAlertDialog(context, R.layout.layout_alert_find_result, 300, 300);
            confirmView = talAlertDialog.getLayout().findViewById(R.id.alert_find_result_search_btn);
            cancelView = talAlertDialog.getLayout().findViewById(R.id.alert_find_result_cancel_btn);
        }else if(alertEnum == AlertEnum.REQUESTING){
            talAlertDialog = new TalAlertDialog(context, R.layout.layout_alert_requesting, 300, 300);
            confirmView = talAlertDialog.getLayout().findViewById(R.id.alert_requesting_search_btn);
            cancelView = talAlertDialog.getLayout().findViewById(R.id.alert_requesting_cancel_btn);

            CircleImageView requestingProfileImage = (CircleImageView) talAlertDialog.getLayout().findViewById(R.id.alert_requesting_image_view);
            Picasso.with(context).load(ResponseController.getNavigationModel().getRelationRequestModel().getProfile_img())
                    .resize(200, 200).into(requestingProfileImage);

            TextView requestingNickname = (TextView) talAlertDialog.getLayout().findViewById(R.id.alert_requesting_nickname_text_view);
            requestingNickname.setText(ResponseController.getNavigationModel().getRelationRequestModel().getNickname());

        }else if(alertEnum == AlertEnum.ACCEPT){
            talAlertDialog = new TalAlertDialog(context, R.layout.layout_alert_partner_accept, 300, 300);
            confirmView = talAlertDialog.getLayout().findViewById(R.id.alert_partner_accept_accept_btn);
            cancelView = talAlertDialog.getLayout().findViewById(R.id.alert_partner_accept_cancel_btn);
        }
    }

    public void show(){
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertEnum == AlertEnum.FIND_ID){
                    requestGCMFindNickname();
                }else if(alertEnum == AlertEnum.RESULT){
                    alertListener.confirm();
                }else if(alertEnum == AlertEnum.REQUESTING){
                    //TODO 다시 요청하기
                    requestRelation(ResponseController.getNavigationModel().getRelationRequestModel().getId());

                }else if(alertEnum == AlertEnum.ACCEPT){
                    //TODO 승인하기
                    alertListener.confirm();
                    talAlertDialog.dismiss();
                }
            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alertEnum == AlertEnum.REQUESTING){
                    requestRelationDelete();
                }else if(alertEnum == AlertEnum.ACCEPT){
                    //TODO DELTE 하기
                    alertListener.cancel();
                    talAlertDialog.dismiss();
                }else{
                    alertListener.cancel();
                    talAlertDialog.dismiss();
                }
                //TODO DELETE
            }
        });

        talAlertDialog.show();
    }

    public View getLayout(){
        return talAlertDialog.getLayout();
    }

    public void dismiss(){
        talAlertDialog.dismiss();
    }

    private void requestGCMFindNickname(){
        String nickname = ((EditText)editTextView).getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestInterface requestInterface = new RequestRelation(context,"check/", responseListnerRelationCheck);

        RequestController requestController = new RequestController(requestInterface, RequestMethod.POST);
        requestController.setBasicHeader();
        requestController.setBody(jsonObject);
        requestController.startRequest();
    }

    ResponseListner responseListnerRelationCheck = new ResponseListner() {
        @Override
        public void getResponse(final String response) {
            LogManager.printLog(getClass(), "responseListnerRelationCheck : " + response);

            talAlertDialog.dismiss();

            //요청 결과 alert 창
            newAlertView = new AlertView(context, AlertEnum.RESULT, new AlertListener() {
                @Override
                public void cancel() {
                    newAlertView.dismiss();

                }

                @Override
                public void confirm() {
                    LogManager.printLog(getClass(), "RequestRelationCheck confirm");
                    JSONObject responseObject = null;

                    try {
                        responseObject = new JSONObject(response);
                        requestRelation(responseObject.getString("id"));


                        //데이터만 저장하고request했을 때 state를 변경한다.
                        Gson gson = new Gson();
                        RelationRequestModel relationRequestModel = gson.fromJson(response, RelationRequestModel.class);

                        ResponseController.getNavigationModel().setRelationRequestModel(relationRequestModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            setResultAlert(newAlertView, response);
            newAlertView.show();
        }

        @Override
        public void successed() {
            alertListener.confirm();
            talAlertDialog.dismiss();
        }

        @Override
        public void failed(int responseCode, String error) {

        }
    };

    private void setResultAlert(AlertView alertView, String response){
        LogManager.printLog(getClass(), "responseListnerRelationCheck : " + response);
        try {
            JSONObject jsonObject  = new JSONObject(response);
            View view = alertView.talAlertDialog.getLayout();
            Picasso.with(context).load(jsonObject.getString("profile_img"))
                    .resize(100,100)
                    .centerCrop()
                    .into((CircleImageView) view.findViewById(R.id.alert_find_result_image_view));

            ((TextView)view.findViewById(R.id.alert_find_result_nickname_text_view)).setText(jsonObject.getString("nickname"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestRelation(String id){

        JSONObject newObject = null;
        try {

            newObject = new JSONObject();
            newObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestInterface requestInterface = new RequestRelation(context,"request/", responseListnerRelationRequest);

        RequestController requestController = new RequestController(requestInterface, RequestMethod.POST);
        requestController.setBasicHeader();
        requestController.setBody(newObject);
        requestController.startRequest();
    }

    ResponseListner responseListnerRelationRequest = new ResponseListner() {
        @Override
        public void getResponse(final String response) {

            RelationState.getInstance().setValue(RelationState.RELATION_INVITING);

            newAlertView.dismiss();
            alertListener.confirm();
            Toast.makeText(context, "요청 되었습니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void successed() {

        }

        @Override
        public void failed(int responseCode, String error) {

        }
    };

    private void requestRelationDelete(){

        RequestInterface requestInterface = new RequestRelation(context,"request/", responseListnerRelationDelete);
        RequestController requestController = new RequestController(requestInterface, RequestMethod.DELETE);
        requestController.setBasicHeader();
        requestController.startRequest();
    }

    ResponseListner responseListnerRelationDelete = new ResponseListner() {
        @Override
        public void getResponse(final String response) {
            RelationState.getInstance().setValue(RelationState.RELATION_NO);
            alertListener.cancel();
            talAlertDialog.dismiss();
        }

        @Override
        public void successed() {
        }

        @Override
        public void failed(int responseCode, String error) {

        }
    };
}
