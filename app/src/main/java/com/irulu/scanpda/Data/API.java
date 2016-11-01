package com.irulu.scanpda.Data;

/**
 * Created by dtw on 16/10/15.
 */

public class API {
    private static API api = null;

    private String host_locale = "";
    private String host_internet = "";
    private String host_test = "http://192.168.1.112:9005";

    private String host = host_internet;

    private String urlVersion = "/api/GetVersion";
    private String urlLogin = "/api/LoginCheck";
    private String boxInfoUrl = "/api/PurchaseRecive/GetSupplierBoxNumber";
    private String confirmByBoxNumUrl = "/api/PurchaseRecive/Receipt_BoxNumber";
    private String formNumInfoUrl = "/api/PurchaseRecive/GetReceiveReceiptList";
    private String confirmReceiveByFormNumUrl = "/api/PurchaseRecive/Receipt_ReceiptNO";

    private API() {
    }

    public static API getInstance() {
        if (api == null) {
            api = new API();
        }
        return api;
    }

    public void setHostLocation(int location) {
        switch (location) {
            case 0:
                host = host_locale;
                break;
            case 1:
                host = host_internet;
                break;
            case 2:
                host = host_test;
                break;
        }
    }

    public String getUrlLogin() {
        return host + urlLogin;
    }

    public String getUrlVersion() {
        return host + urlVersion;
    }


    public String getConfirmByBoxNumUrl() {
        return host + confirmByBoxNumUrl;
    }

    public String getBoxInfoUrl() {
        return host + boxInfoUrl;
    }

    public String getFormNumInfoUrl() {
        return host + formNumInfoUrl;
    }

    public String getConfirmReceiveByFormNumUrl() {
        return host + confirmReceiveByFormNumUrl;
    }
}
