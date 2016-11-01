package com.irulu.scanpda.Model.AdapterItemModel;

import android.graphics.Bitmap;

/**
 * Created by dtw on 16/10/24.
 */

public class CardItemModle {
    private Bitmap itemImage;
    private String itemName;

    public Bitmap getItemImage() {
        return itemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
