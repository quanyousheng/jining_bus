package com.whpe.qrcode.shandong_jining.toolbean;

/**
 * Created by yang on 2018/9/30.
 */

public class ConsumrecordsBean {
    private String date;
    private String paytype;
    private String money;
    private String payPurpose;
    private String detaildinfo;

    public String getDetaildinfo() {
        return detaildinfo;
    }

    public void setDetaildinfo(String detaildinfo) {
        this.detaildinfo = detaildinfo;
    }

    public String getPayPurpose() {
        return payPurpose;
    }

    public void setPayPurpose(String payPurpose) {
        this.payPurpose = payPurpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
