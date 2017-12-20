package com.tbx.user.wfm.PreviousPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 11/30/2017.
 */

public class Datum {

    @SerializedName("AWB_no")
    @Expose
    private String aWBNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("adddress")
    @Expose
    private String adddress;
    @SerializedName("paymenttype")
    @Expose
    private String paymenttype;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("undeliveredRemark")
    @Expose
    private String undeliveredRemark;
    @SerializedName("status")
    @Expose
    private String status;

    public String getAWBNo() {
        return aWBNo;
    }

    public void setAWBNo(String aWBNo) {
        this.aWBNo = aWBNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAdddress() {
        return adddress;
    }

    public void setAdddress(String adddress) {
        this.adddress = adddress;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUndeliveredRemark() {
        return undeliveredRemark;
    }

    public void setUndeliveredRemark(String undeliveredRemark) {
        this.undeliveredRemark = undeliveredRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
