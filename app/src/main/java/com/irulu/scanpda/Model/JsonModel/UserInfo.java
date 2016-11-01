package com.irulu.scanpda.Model.JsonModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dtw on 16/10/19.
 */

public class UserInfo implements JsonModel,Parcelable{

    /**
     * success : 1
     * message :
     * adminName : carol.yi
     * warehouseCode : z
     * areaCode : 1006
     * power : a,b,pca,pcb
     */

    private int success;
    private String message;
    private String adminName;
    private String warehouseCode;
    private String areaCode;
    private String power;

    protected UserInfo(Parcel in) {
        success = in.readInt();
        message = in.readString();
        adminName = in.readString();
        warehouseCode = in.readString();
        areaCode = in.readString();
        power = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPositionCode() {
        return warehouseCode;
    }

    public void setPositionCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(success);
        dest.writeString(message);
        dest.writeString(adminName);
        dest.writeString(warehouseCode);
        dest.writeString(areaCode);
        dest.writeString(power);
    }
}
