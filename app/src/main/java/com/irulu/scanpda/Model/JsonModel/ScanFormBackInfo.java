package com.irulu.scanpda.Model.JsonModel;

import java.util.List;

/**
 * Created by dtw on 16/10/27.
 */

public class ScanFormBackInfo implements JsonModel{

    /**
     * resultId : 1
     * data : [{"SKU":"Y0003","SN":"Y0003NCC1610123A#031","Qty":30},{"SKU":"Y0003","SN":"Y0003NCC1610123A#032","Qty":30},{"SKU":"Y0003","SN":"Y0003NCC1610123A#033","Qty":30}]
     */

    private int resultId;

    private String msg;

    /**
     * SKU : Y0003
     * SN : Y0003NCC1610123A#031
     * Qty : 30
     */
    private List<SkuSnQtyBean> data;

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

    public List<SkuSnQtyBean> getData() {
        return data;
    }

    public void setData(List<SkuSnQtyBean> data) {
        this.data = data;
    }
}
