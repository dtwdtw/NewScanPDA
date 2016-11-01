package com.irulu.scanpda.Model.JsonModel;

import java.util.List;

/**
 * Created by dtw on 16/10/25.
 */

public class ScanBoxBackInfo implements JsonModel {

    /**
     * resultId : 1
     * data : [{"SKU":"Y0003","SN":"Y0003NCC1610123A#001","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#002","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#003","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#004","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#005","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#006","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#007","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#008","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#009","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#010","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#011","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#012","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#013","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#014","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#015","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#016","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#017","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#018","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#019","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#020","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#021","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#022","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#023","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#024","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#025","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#026","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#027","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#028","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#029","Qty":1},{"SKU":"Y0003","SN":"Y0003NCC1610123A#030","Qty":1}]
     */

    private int resultId;
    private String msg;


    /**
     * SKU : Y0003
     * SN : Y0003NCC1610123A#001
     * Qty : 1
     */

    private List<SkuSnQtyBean> data;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public List<SkuSnQtyBean> getData() {
        return data;
    }

    public void setData(List<SkuSnQtyBean> data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
