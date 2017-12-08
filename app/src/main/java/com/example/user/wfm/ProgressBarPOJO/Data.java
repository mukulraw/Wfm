package com.example.user.wfm.ProgressBarPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TBX on 12/7/2017.
 */

public class Data {


    @SerializedName("total_order")
    @Expose
    private String totalOrder;
    @SerializedName("total_undelivered_order")
    @Expose
    private String totalUndeliveredOrder;
    @SerializedName("total_delivered_order")
    @Expose
    private String totalDeliveredOrder;
    @SerializedName("total_collectable_amount")
    @Expose
    private String totalCollectableAmount;

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getTotalUndeliveredOrder() {
        return totalUndeliveredOrder;
    }

    public void setTotalUndeliveredOrder(String totalUndeliveredOrder) {
        this.totalUndeliveredOrder = totalUndeliveredOrder;
    }

    public String getTotalDeliveredOrder() {
        return totalDeliveredOrder;
    }

    public void setTotalDeliveredOrder(String totalDeliveredOrder) {
        this.totalDeliveredOrder = totalDeliveredOrder;
    }

    public String getTotalCollectableAmount() {
        return totalCollectableAmount;
    }

    public void setTotalCollectableAmount(String totalCollectableAmount) {
        this.totalCollectableAmount = totalCollectableAmount;
    }

}
