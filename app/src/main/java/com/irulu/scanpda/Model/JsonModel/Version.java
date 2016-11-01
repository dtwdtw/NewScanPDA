package com.irulu.scanpda.Model.JsonModel;

/**
 * Created by dtw on 16/10/17.
 */

public class Version implements JsonModel{
    /**
     * msg : 1.98
     */
    private String msg;

    public String getVersion() {
        return msg;
    }

    public void setVersion(String version) {
        this.msg = version;
    }
}
