package com.wowell.talboro2.utils.http;

import com.wowell.talboro2.controller.TokenController;
import com.wowell.talboro2.model.Header;

import java.util.ArrayList;

/**
 * Created by kim on 2016-06-17.
 */
public class HeaderManager {
    ArrayList<Header> headerArrayList;

    public HeaderManager(Builder builder) {
        headerArrayList = builder.getHeaderArrayList();
    }

    public ArrayList<Header> getHeaderArrayList() {
        return headerArrayList;
    }

    public void setHeaderArrayList(ArrayList<Header> headerArrayList) {
        this.headerArrayList = headerArrayList;
    }

    public static class Builder {
        ArrayList<Header> headerArrayList = new ArrayList<>();

        public Builder addToken(){
            headerArrayList.add(new Header("Authorization", "Token " + TokenController.getInstance().getToken()));
            return this;
        }

        public Builder addContentType(){
            headerArrayList.add(new Header("Content-Type", "application/json"));
            return this;
        }

        public Builder addHeader(String key, String value){
            headerArrayList.add(new Header(key, value));
            return this;
        }

        public ArrayList<Header> getHeaderArrayList() {
            return headerArrayList;
        }

        public void setHeaderArrayList(ArrayList<Header> headerArrayList) {
            this.headerArrayList = headerArrayList;
        }

        public HeaderManager build() {
            return new HeaderManager(this);
        }
    }




}
