package com.irulu.scanpda.Model.JsonModel;

/**
 * Created by dtw on 16/10/25.
 */

public class SkuSnQtyBean {
    private String SKU;
    private String SN;
    private int Qty;

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }
}
