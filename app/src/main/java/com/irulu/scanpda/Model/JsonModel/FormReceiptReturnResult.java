package com.irulu.scanpda.Model.JsonModel;

/**
 * Created by dtw on 16/10/27.
 */

public class FormReceiptReturnResult implements JsonModel {

    /**
     * resultId : 1
     * msg : 收货成功!
     */

    private int resultId;
    private String msg;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
